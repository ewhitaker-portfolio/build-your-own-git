#include "Definitions.h"
#include "io_portfolio_ewhitaker_system_SystemCall.h"

typedef struct {
    jclass clazz;
    jmethodID setType;
} FileStatisticReference;

static FileStatisticReference FileStatistic;

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

static void setStatbuf(JNIEnv* env, struct stat* statbuf, jobject jstatbuf) {
    (*env)->CallVoidMethod(
        env, jstatbuf, FileStatistic.setType, statbuf->st_mode & S_IFMT
    );
}

/*
 * Class:     io_portfolio_ewhitaker_system_SystemCall
 * Method:    stat
 * Signature:
 * (Ljava/lang/String;Lio/portfolio/ewhitaker/system/file/FileStatistic;)I
 */
JNIEXPORT jint JNICALL Java_io_portfolio_ewhitaker_system_SystemCall_stat(
    JNIEnv* env, jclass clazz, jstring jpathname, jobject jstatbuf
) {
    INT result;

    STRING pathname = (*env)->GetStringUTFChars(env, jpathname, NULL);
    struct stat statbuf;

    register STRING arg1 asm("rdi") = pathname;
    register struct stat* arg2 asm("rsi") = &statbuf;

    asm volatile("syscall\n\t"
                 : "=a"(result)
                 : "0"(__NR_stat), "r"(arg1), "r"(arg2)
                 : "memory", "cc", "r11", "cx");

    (*env)->ReleaseStringUTFChars(env, jpathname, pathname);

    if (result != 0) {
        return result;
    }

    setStatbuf(env, &statbuf, jstatbuf);
    return result;
}

/*
 * Class:     io_portfolio_ewhitaker_system_SystemCall
 * Method:    fstat
 * Signature: (ILio/portfolio/ewhitaker/system/file/FileStatistic;)I
 */
JNIEXPORT jint JNICALL Java_io_portfolio_ewhitaker_system_SystemCall_fstat(
    JNIEnv* env, jclass clazz, jint jfd, jobject jstatbuf
) {
    INT result;

    struct stat statbuf;

    register INT arg1 asm("rdi") = jfd;
    register struct stat* arg2 asm("rsi") = &statbuf;

    asm volatile("syscall\n\t"
                 : "=a"(result)
                 : "0"(__NR_fstat), "r"(arg1), "r"(arg2)
                 : "memory", "cc", "r11", "cx");

    if (result != 0) {
        return result;
    }

    setStatbuf(env, &statbuf, jstatbuf);
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
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_io_portfolio_ewhitaker_system_SystemCall_init(JNIEnv* env, jclass clazz) {
    FileStatistic.clazz = (*env)->FindClass(
        env, "io/portfolio/ewhitaker/system/file/FileStatistic"
    );
    FileStatistic.setType =
        (*env)->GetMethodID(env, FileStatistic.clazz, "setType", "(I)V");
}
