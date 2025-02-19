#ifndef CRAFTING_INTERPRETERS_DEFINITIONS_H
#define CRAFTING_INTERPRETERS_DEFINITIONS_H

#include <sys/syscall.h>
#include <sys/stat.h>
#include <sys/fcntl.h>
#include <sys/dir.h>

#ifdef __cplusplus
extern "C" {
#endif

#define PUBLIC extern
#define PRIVATE static

#define STRING const char*
#define BYTE signed char
#define INT signed int
#define LONG signed long int

#define MIN(a, b) (a < b ? a : b)
#define MAX(a, b) (a > b ? a : b)

struct linux_dirent {
    unsigned long  d_ino;       /* Inode number */
    unsigned long  d_off;       /* Offset to next linux_dirent */
    unsigned short d_reclen;    /* Length of this linux_dirent */
    char           d_name[256]; /* Filename (null-terminated) */
                                /* length is actually (d_reclen - 2 - offsetof(struct linux_dirent, d_name)) */
    char           pad;         /* Zero padding byte */
    char           d_type;      /* File type (only since Linux 2.6.4); offset is (d_reclen - 1) */
};

#ifdef __cplusplus
}
#endif

#endif
