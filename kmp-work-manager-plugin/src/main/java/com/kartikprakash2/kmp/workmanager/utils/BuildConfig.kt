/*
 * Copyright 2025 Kartik Prakash
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.kartikprakash2.kmp.workmanager.utils

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File

fun writeKmpBackgroundJobTypes(
    sourceSetDirectory: File,
    packageName: String,
    className: String,
    jobIdentifiers: Set<String>
) {
    val interfaceName = ClassName("com.kartikprakash2.multiplatform.tools.models", "BackgroundJobType")
    FileSpec.builder(packageName, className)
        .indent("    ")
        .addFileComment("GENERATED BY build.gradle.kts -- DO NOT MODIFY")
        .also {
            it.addImport(packageName = "com.kartikprakash2.multiplatform.tools.models", "BackgroundJobType")
        }
        .addType(
            TypeSpec.enumBuilder(className)
                .addSuperinterface(interfaceName)
                .apply {
                    jobIdentifiers.forEach { id ->
                        addEnumConstant(
                            id,
                            TypeSpec.anonymousClassBuilder()
                                .addProperty(PropertySpec.builder("identifier", String::class)
                                    .addModifiers(KModifier.OVERRIDE)
                                    .initializer("%S", id)
                                    .build())
                                .build()
                        )
                    }
                }
                .build()
        )
        .build()
        .writeTo(sourceSetDirectory)
}