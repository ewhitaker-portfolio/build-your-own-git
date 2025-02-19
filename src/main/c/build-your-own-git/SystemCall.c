#include "Definitions.h"
#include "Utility.h"
#include "io_portfolio_ewhitaker_system_SystemCall.h"
#include "jni.h"

typedef struct {
    jmethodID constructor;
    jmethodID setType;
    jmethodID setBlockSize;
} FileStatisticReference;

static FileStatisticReference FileStatistic;

static void setStatbuf(JNIEnv* env, struct stat* statbuf, jobject jstatbuf) {
    (*env)->CallVoidMethod(
        env, jstatbuf, FileStatistic.setType, statbuf->st_mode & S_IFMT
    );
    (*env)->CallVoidMethod(
        env, jstatbuf, FileStatistic.setBlockSize, statbuf->st_blksize
    );
}

typedef struct {
    jmethodID constructor;
    jmethodID setName;
} DirectoryEntryReference;

static DirectoryEntryReference DirectoryEntry;

static void
setDirent(JNIEnv* env, struct linux_dirent* dirent, jobject jdirent) {
    (*env)->CallVoidMethod(
        env, jdirent, DirectoryEntry.setName,
        (*env)->NewStringUTF(env, dirent->d_name)
    );
}

static inline bool isCurrentOrParentDirectory(const char* name) {
    return compareString(".", name) == 0 || compareString("..", name) == 0;
}

/*
 * Class:     io_portfolio_ewhitaker_system_SystemCall
 * Method:    open
 * Signature: (Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL
Java_io_portfolio_ewhitaker_system_SystemCall_open__Ljava_lang_String_2I(
    JNIEnv* env, jclass clazz, jstring jpathname, jint jflags
) {
    INT result;

    STRING pathname = (*env)->GetStringUTFChars(env, jpathname, NULL);

    register STRING arg1 asm("rdi") = pathname;
    register INT arg2 asm("rsi") = jflags;

    asm volatile("syscall\n\t"
                 : "=a"(result)
                 : "0"(__NR_open), "r"(arg1), "r"(arg2)
                 : "memory", "cc", "r11", "cx");

    (*env)->ReleaseStringUTFChars(env, jpathname, pathname);

    return result;
}

/*
 * Class:     io_portfolio_ewhitaker_system_SystemCall
 * Method:    open
 * Signature: (Ljava/lang/String;IJ)I
 */
JNIEXPORT jint JNICALL
Java_io_portfolio_ewhitaker_system_SystemCall_open__Ljava_lang_String_2IJ(
    JNIEnv* env, jclass clazz, jstring jpathname, jint jflags, jlong jmode
) {
    INT result;

    STRING pathname = (*env)->GetStringUTFChars(env, jpathname, NULL);

    register STRING arg1 asm("rdi") = pathname;
    register INT arg2 asm("rsi") = jflags;
    register unsigned int arg3 asm("rdx") = jmode;

    asm volatile("syscall\n\t"
                 : "=a"(result)
                 : "0"(__NR_open), "r"(arg1), "r"(arg2), "r"(arg3)
                 : "memory", "cc", "r11", "cx");

    (*env)->ReleaseStringUTFChars(env, jpathname, pathname);

    return result;
}

/*
 * Class:     io_portfolio_ewhitaker_system_SystemCall
 * Method:    close
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_io_portfolio_ewhitaker_system_SystemCall_close(
    JNIEnv* env, jclass clazz, jint jfd
) {
    INT result;

    register INT arg1 asm("rdi") = jfd;

    asm volatile("syscall\n\t"
                 : "=a"(result)
                 : "0"(__NR_close), "r"(arg1)
                 : "memory", "cc", "r11", "cx");

    return result;
}

static INT syscall_stat(STRING pathname, struct stat* statbuf) {
    INT result;

    register STRING arg1 asm("rdi") = pathname;
    register struct stat* arg2 asm("rsi") = statbuf;

    asm volatile("syscall\n\t"
                 : "=a"(result)
                 : "0"(__NR_stat), "r"(arg1), "r"(arg2)
                 : "memory", "cc", "r11", "cx");

    return result;
}

/*
 * Class:     io_portfolio_ewhitaker_system_SystemCall
 * Method:    stat
 * Signature:
 * (Ljava/lang/String;)Lio/portfolio/ewhitaker/system/file/FileStatistic;
 */
JNIEXPORT jobject JNICALL Java_io_portfolio_ewhitaker_system_SystemCall_stat(
    JNIEnv* env, jclass clazz, jstring jpathname
) {
    STRING pathname = (*env)->GetStringUTFChars(env, jpathname, NULL);
    struct stat statbuf;

    INT result = syscall_stat(pathname, &statbuf);

    (*env)->ReleaseStringUTFChars(env, jpathname, pathname);

    if (result != 0) {
        return NULL;
    }

    jclass statclass = (*env)->FindClass(
        env, "io/portfolio/ewhitaker/system/file/FileStatistic"
    );
    jobject jstatbuf =
        (*env)->NewObject(env, statclass, FileStatistic.constructor);
    setStatbuf(env, &statbuf, jstatbuf);
    return jstatbuf;
}

static INT syscall_fstat(INT fd, struct stat* statbuf) {
    INT result;

    register INT arg1 asm("rdi") = fd;
    register struct stat* arg2 asm("rsi") = statbuf;

    asm volatile("syscall\n\t"
                 : "=a"(result)
                 : "0"(__NR_fstat), "r"(arg1), "r"(arg2)
                 : "memory", "cc", "r11", "cx");
    return result;
}

/*
 * Class:     io_portfolio_ewhitaker_system_SystemCall
 * Method:    fstat
 * Signature: (I)Lio/portfolio/ewhitaker/system/file/FileStatistic;
 */
JNIEXPORT jobject JNICALL Java_io_portfolio_ewhitaker_system_SystemCall_fstat(
    JNIEnv* env, jclass clazz, jint jfd
) {
    struct stat statbuf;

    INT result = syscall_fstat(jfd, &statbuf);

    if (result != 0) {
        return NULL;
    }

    jclass statclass = (*env)->FindClass(
        env, "io/portfolio/ewhitaker/system/file/FileStatistic"
    );
    jobject jstatbuf =
        (*env)->NewObject(env, statclass, FileStatistic.constructor);
    setStatbuf(env, &statbuf, jstatbuf);
    return jstatbuf;
}

static INT syscall_getdents(unsigned int fd, void* dirp, unsigned int count) {
    INT result;

    register unsigned int arg1 asm("rdi") = fd;
    register void* arg2 asm("rsi") = dirp;
    register unsigned int arg3 asm("rdx") = count;

    asm volatile("syscall\n\t"
                 : "=a"(result)
                 : "0"(__NR_getdents), "r"(arg1), "r"(arg2), "r"(arg3)
                 : "memory", "cc", "r11", "cx");

    return result;
}

/*
 * Class:     io_portfolio_ewhitaker_system_SystemCall
 * Method:    getdents
 * Signature: (IJ)[Lio/portfolio/ewhitaker/system/file/DirectoryEntry;
 */
JNIEXPORT jobjectArray JNICALL
Java_io_portfolio_ewhitaker_system_SystemCall_getdents(
    JNIEnv* env, jclass clazz, jint jfd, jlong jcount
) {
    INT index = 0;
    INT capacity = 16;

    jclass direntclass = (*env)->FindClass(
        env, "io/portfolio/ewhitaker/system/file/DirectoryEntry"
    );
    jobject jdirp = (*env)->NewObjectArray(env, capacity, direntclass, NULL);

    BYTE dirp[jcount];

    INT offset = 0;
    INT size = syscall_getdents(jfd, dirp, jcount);

    while (size > 0 && offset < size) {
        if (index + 1 >= capacity) {
            const int oldCapacity = capacity;
            jobject newJdirp =
                (*env)->NewObjectArray(env, capacity <<= 1, direntclass, NULL);
            for (int i = 0; i < oldCapacity; ++i) {
                (*env)->SetObjectArrayElement(
                    env, newJdirp, i,
                    (*env)->GetObjectArrayElement(env, jdirp, i)
                );
            }
            (*env)->DeleteLocalRef(env, jdirp);
            jdirp = newJdirp;
        }

        struct linux_dirent* dirent = (struct linux_dirent*)&dirp[offset];
        offset += dirent->d_reclen;
        if (offset >= size) {
            size = syscall_getdents(jfd, dirp, jcount);
        }
        if (isCurrentOrParentDirectory(dirent->d_name)) {
            continue;
        }
        jobject jdirent =
            (*env)->NewObject(env, direntclass, DirectoryEntry.constructor);
        setDirent(env, dirent, jdirent);
        (*env)->SetObjectArrayElement(env, jdirp, index++, jdirent);
    }

    jobject result = (*env)->NewObjectArray(env, index, direntclass, NULL);
    for (int i = 0; i < index; ++i) {
        (*env)->SetObjectArrayElement(
            env, result, i, (*env)->GetObjectArrayElement(env, jdirp, i)
        );
    }
    (*env)->DeleteLocalRef(env, jdirp);

    return result;
}

/*
 * Class:     io_portfolio_ewhitaker_system_SystemCall
 * Method:    mkdir
 * Signature: (Ljava/lang/String;J)I
 */
JNIEXPORT jint JNICALL Java_io_portfolio_ewhitaker_system_SystemCall_mkdir(
    JNIEnv* env, jclass clazz, jstring jpathname, jlong jmode
) {
    INT result;

    STRING pathname = (*env)->GetStringUTFChars(env, jpathname, NULL);
    unsigned int mode = jmode;

    register STRING arg1 asm("rdi") = pathname;
    register unsigned int arg2 asm("rsi") = mode;

    asm volatile("syscall\n\t"
                 : "=a"(result)
                 : "0"(__NR_mkdir), "r"(arg1), "r"(arg2)
                 : "memory", "cc", "r11", "cx");

    (*env)->ReleaseStringUTFChars(env, jpathname, pathname);

    return result;
}

/*
 * Class:     io_portfolio_ewhitaker_system_SystemCall
 * Method:    creat
 * Signature: (Ljava/lang/String;J)I
 */
JNIEXPORT jint JNICALL Java_io_portfolio_ewhitaker_system_SystemCall_creat(
    JNIEnv* env, jclass clazz, jstring jpathname, jlong jmode
) {
    INT result;

    STRING pathname = (*env)->GetStringUTFChars(env, jpathname, NULL);
    unsigned int mode = jmode;

    register STRING arg1 asm("rdi") = pathname;
    register unsigned int arg2 asm("rsi") = mode;

    asm volatile("syscall\n\t"
                 : "=a"(result)
                 : "0"(__NR_creat), "r"(arg1), "r"(arg2)
                 : "memory", "cc", "r11", "cx");

    (*env)->ReleaseStringUTFChars(env, jpathname, pathname);

    return result;
}

/*
 * Class:     io_portfolio_ewhitaker_system_SystemCall
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_io_portfolio_ewhitaker_system_SystemCall_init(JNIEnv* env, jclass clazz) {
    jclass statclass = (*env)->FindClass(
        env, "io/portfolio/ewhitaker/system/file/FileStatistic"
    );
    FileStatistic.constructor =
        (*env)->GetMethodID(env, statclass, "<init>", "()V");
    FileStatistic.setType =
        (*env)->GetMethodID(env, statclass, "setType", "(I)V");
    FileStatistic.setBlockSize =
        (*env)->GetMethodID(env, statclass, "setBlockSize", "(J)V");

    jclass direntclass = (*env)->FindClass(
        env, "io/portfolio/ewhitaker/system/file/DirectoryEntry"
    );
    DirectoryEntry.constructor =
        (*env)->GetMethodID(env, direntclass, "<init>", "()V");
    DirectoryEntry.setName = (*env)->GetMethodID(
        env, direntclass, "setName", "(Ljava/lang/String;)V"
    );
}
