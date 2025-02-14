cmake_minimum_required(VERSION 3.26)

set(CMAKE_BUILD_TYPE "Debug")
set(CMAKE_C_STANDARD 23)
set(CMAKE_C_COMPILER "clang")

project(build-your-own-git VERSION 0.0.1 LANGUAGES C)

file(GLOB_RECURSE INCLUDES src/*.h)
file(GLOB_RECURSE SOURCES src/*.c)

add_library(build-your-own-git SHARED ${INCLUDES} ${SOURCES})
target_include_directories(
    build-your-own-git PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/src/main/c/include/build-your-own-git
    $ENV{JAVA_HOME}/include $ENV{JAVA_HOME}/include/linux
)

file(GLOB_RECURSE PUBLIC_INCLUDES src/main/c/include/build-your-own-git/*.h)
target_sources(
    build-your-own-git INTERFACE
    FILE_SET HEADERS
    BASE_DIRS ${CMAKE_CURRENT_SOURCE_DIR}/src/main/c/include
    FILES ${PUBLIC_INCLUDES}
)

install(
    TARGETS build-your-own-git LIBRARY FILE_SET HEADERS
)
