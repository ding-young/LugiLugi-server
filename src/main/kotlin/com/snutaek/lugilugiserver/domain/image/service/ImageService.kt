package com.snutaek.lugilugiserver.domain.image.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.multipart.MultipartFile
import com.snutaek.lugilugiserver.domain.user.model.User
import com.snutaek.lugilugiserver.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Configuration
class AWSConfiguration {
    @Bean
    fun assetS3Client(
        @Value("\${aws.access-key}") accessKey: String,
        @Value("\${aws.secret-key}") secretKey: String,
        @Value("\${aws.s3.endpoint}") s3Endpoint: String
    ): AmazonS3 {
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey)))
            .withRegion(Regions.AP_NORTHEAST_2)
            .build()
    }
}

// honestly this is... S3 service
@Service
class ImageService(
    private val userRepository: UserRepository
) {
    @Value("\${aws.s3.bucket}")
    lateinit var bucketName: String
    @Value("\${aws.s3.endpoint}")
    lateinit var s3Endpoint: String
    @Value("\${aws.s3.endpoint}")
    lateinit var defaultProfileImagePath: String

    @Autowired
    lateinit var amazonS3: AmazonS3

    fun upload(file: MultipartFile, folderName: String, fileToken: String): String {
        val fileName = file.originalFilename
        val keyName = "images/$folderName/$fileToken/$fileName"   // random uuid token = fileToken?
        val inputStream = file.inputStream
        val meta = ObjectMetadata()
        meta.contentLength = inputStream.available().toLong()
        meta.contentType = file.contentType

        amazonS3.putObject(bucketName, keyName, inputStream, meta)
        return "${s3Endpoint}/${keyName}"
    }

    fun uploadUserProfileImage(image: MultipartFile, user: User): String {
        val randomToken = UUID.randomUUID().toString()
        val imageS3url = upload(image, "user/${user.username}", randomToken)
        // TODO refactor
        user.profileS3ObjectKey = imageS3url
        userRepository.save(user)
        return imageS3url
    }

    fun setUserDefaultProfileImage(user: User): String {
        val defaultImageS3Url = "${s3Endpoint}/${defaultProfileImagePath}"
        user.profileS3ObjectKey = defaultImageS3Url
        userRepository.save(user)
        return defaultImageS3Url
    }
}