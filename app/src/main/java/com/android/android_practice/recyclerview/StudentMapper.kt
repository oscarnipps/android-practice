package com.android.android_practice.recyclerview

object StudentMapper {

    fun mapToStudentHeader(students: List<Student>): Map<Char, List<Student>> {
        return students.groupBy { it.name[0] }
    }
}