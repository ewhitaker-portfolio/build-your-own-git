#ifndef CRAFTING_INTERPRETERS_DEFINITIONS_H
#define CRAFTING_INTERPRETERS_DEFINITIONS_H

#ifdef __cplusplus
extern "C" {
#endif

#define PUBLIC extern
#define PRIVATE static

#define STRING const char*
#define INT signed int
#define LONG signed long int

#define SYSCALL_MKDIR 83
#define SYSCALL_CREAT 85

#ifdef __cplusplus
}
#endif

#endif
