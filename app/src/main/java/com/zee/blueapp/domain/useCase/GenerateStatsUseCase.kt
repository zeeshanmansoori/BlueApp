package com.zee.blueapp.domain.useCase

import com.zee.blueapp.domain.models.Topic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenerateStatsUseCase {

    suspend operator fun invoke(carousel: Topic): String = withContext(Dispatchers.IO) {

        val photos = carousel.photos

        if (photos.isNullOrEmpty()) {
            return@withContext ""
        }

        val hashMap = mutableMapOf<Char, Int>()

        var first = Pair('a', 0)
        var second = Pair('a', 0)
        var third = Pair('a', 0)


        photos.forEach { photo ->

            photo.userName.forEach {
                val char = it.lowercaseChar()
                val oldC = hashMap[char] ?: 0
                val count = oldC + 1
                hashMap[char] = count

            }
        }

        hashMap.entries.forEach {

            val firstCount = first.second
            val secondCount = second.second
            val thirdCount = third.second
            val count = it.value
            val char = it.key

            when {
                count > firstCount -> {
                    third = second
                    second = first
                    first = char to count
                }

                count > secondCount -> {
                    third = second
                    second = char to count
                }

                count > thirdCount -> {
                    third = char to count
                }
            }
        }
        return@withContext "$first,$second,$third"
    }
}