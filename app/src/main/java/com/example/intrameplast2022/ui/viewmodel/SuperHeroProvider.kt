package com.example.intrameplast2022.ui.viewmodel

import CourseModal

class SuperHeroProvider {
    companion object {
        val superheroList = listOf<CourseModal>(
            CourseModal(
                "Spiderman",
                "Marvel"
            ),
            CourseModal(
                "Daredevil",
                "Marvel"
            ),
            CourseModal(
                "Wolverine",
                "Marvel"
            ),
            CourseModal(
                "Batman",
                "DC"
            ),
            CourseModal(
                "Thor",
                "Marvel"
            ),
            CourseModal(
                "Flash",
                "DC"
            ),
            CourseModal(
                "Green Lantern",
                "DC"
            ),
            CourseModal(
                "Wonder Woman",
                "DC"
            ),
        )
    }
}