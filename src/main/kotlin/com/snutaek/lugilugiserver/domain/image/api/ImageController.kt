package com.snutaek.lugilugiserver.domain.image.api

import com.snutaek.lugilugiserver.domain.image.dto.ImageDto
import com.snutaek.lugilugiserver.domain.image.service.ImageService
import com.snutaek.lugilugiserver.domain.user.model.User
import com.snutaek.lugilugiserver.domain.user.service.UserService
import com.snutaek.lugilugiserver.global.auth.CurrentUser
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("api/v1/image")
class ImageController(
    private val imageService: ImageService,
) {
    @PostMapping("/me/")  // Should it be in userController..?
    @ResponseStatus(HttpStatus.CREATED)
    fun uploadUserProfileImage(
        @CurrentUser user: User,
        @RequestPart image: MultipartFile,
    ) : ImageDto.ImageUploadResponse {
        return ImageDto.ImageUploadResponse(imageService.uploadUserProfileImage(image, user))
    }

    @PostMapping("/me/default/")  // Should it be in userController..?
    fun setUserDefaultProfileImage(
        @CurrentUser user: User,
    ) : ImageDto.ImageUploadResponse {
        return ImageDto.ImageUploadResponse(imageService.setUserDefaultProfileImage(user))
    }
}