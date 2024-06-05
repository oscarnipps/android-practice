package com.android.android_practice.espresso_practice

import com.android.android_practice.recyclerview.Student
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EspressoPracticeRepo {

    fun getStudents(): List<Student> {
        return mutableListOf(
            Student(1, "James Bun", 23),
            Student(2, "Harvey Barnes bun", 19),
            Student(3, "Bob White", 26),
            Student(4, "Jacob Murphy", 25),
            Student(5, "Lukas pope", 22),
            Student(6, "richard nick", 30),
            Student(7, "phillip burn", 26),
            Student(8, "donald duke", 21),
            Student(9, "Dave shook", 20),
        )
    }

    fun getStudentsFlow(): Flow<List<Student>> {
        return flow {
            //mimic real world delay
            delay(2000)

            emit(
                mutableListOf(
                    Student(1, "James Bun", 23),
                    Student(2, "Harvey Barnes bun", 19),
                    Student(3, "Bob White", 26),
                    Student(4, "Jacob Murphy", 25),
                    Student(5, "Lukas pope", 22),
                    Student(6, "richard nick", 30),
                    Student(7, "phillip burn", 26),
                    Student(8, "donald duke", 21),
                    Student(9, "Dave shook", 20),
                )
            )
        }
    }


}