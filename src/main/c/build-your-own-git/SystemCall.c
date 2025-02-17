#include "Definitions.h"
#include "io_portfolio_ewhitaker_system_SystemCall.h"

typedef struct {
    jclass clazz;
    jmethodID getType;
} FileStatisticsReference;

static FileStatisticsReference FileStatistics;

/*
 * Class:     io_portfolio_ewhitaker_system_SystemCall
 * Method:    stat
 * Signature: (Ljava/lang/String;Lio/portfolio/ewhitaker/system/io/FileStatus;)I
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

    if (result == 0) {
        (*env)->CallVoidMethod(
            env, jstatbuf, FileStatistics.getType, statbuf.st_mode & S_IFMT
        );
    }

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
    FileStatistics.clazz =
        (*env)->FindClass(env, "io/portfolio/ewhitaker/system/file/FileStatus");
    FileStatistics.getType =
        (*env)->GetMethodID(env, FileStatistics.clazz, "setType", "(I)V");
}
