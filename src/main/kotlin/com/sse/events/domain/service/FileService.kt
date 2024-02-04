package com.sse.events.domain.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.fileSize

@Service
class FileService {
    companion object{
        const val FILE_PATH:String ="src\\main\\resources\\video.mp4"
        val logger: Logger = LoggerFactory.getLogger(FileService::class.java)
    }
    fun size():Long{
        val path: Path = Paths.get(FILE_PATH)
        return path.fileSize()
    }

    fun inputStream():InputStream{
        logger.info("Streaming file")
        return FileInputStream(File(FILE_PATH))
    }
}