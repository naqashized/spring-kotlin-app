package com.sse.events.api

import com.sse.events.domain.service.FileService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.concurrent.Executors


@RestController
@RequestMapping("/stream")
class StreamingController(val fileService: FileService) {
    companion object{
        const val FILE_APPEND_PATH ="src\\main\\resources\\1234.mp4"
        val logger: Logger = LoggerFactory.getLogger(StreamingController::class.java)
        const val LIMIT = 5000
    }

    @GetMapping(produces = ["application/octet-stream"])
    fun streamFile(): SseEmitter? {
        var start = 0
        var limit = LIMIT
        var end:Int = LIMIT;
        val emitter = SseEmitter()
        val size = fileService.size()
        logger.info("File size {}", size)

        val inputStream: InputStream = fileService.inputStream()
        createFile()

        val sseMvcExecutor = Executors.newSingleThreadExecutor()
        sseMvcExecutor.execute {
            while (start < size) {
                try{
                    val data = ByteArray(limit)
                    inputStream.readNBytes(data, 0, limit)

                    writeFile(data)
                    start = end

                    limit = Math.min(limit, size.toInt() -end)
                    end = start+limit
                    val event = SseEmitter.event()
                                .data(String(data))
                                .name("Stream")

                    emitter.send(event)
                }catch (exception: IOException){
                    logger.info("Error {}",exception.message)
                }
            }
            emitter.complete()
        }
        return emitter
    }
    fun writeFile(videoData: ByteArray?){
        try{
            Files.write(
                Paths.get(FILE_APPEND_PATH),
                videoData,
                StandardOpenOption.APPEND
            )
        }catch (exception: IOException){
            logger.error(exception.message)
        }
    }
    fun createFile(){
        if(Files.exists(Paths.get(FILE_APPEND_PATH))){
            Files.delete(Paths.get(FILE_APPEND_PATH))
        }
        Files.createFile(Paths.get(FILE_APPEND_PATH))
    }
}
