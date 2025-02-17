#ifndef CRAFTING_INTERPRETERS_DEFINITIONS_H
#define CRAFTING_INTERPRETERS_DEFINITIONS_H

#include <sys/syscall.h>
#include <sys/stat.h>
#include <sys/fcntl.h>

#ifdef __cplusplus
extern "C" {
#endif

#define PUBLIC extern
#define PRIVATE static

#define STRING const char*
#define INT signed int
#define LONG signed long int

#ifdef __cplusplus
}
#endif

#endif
