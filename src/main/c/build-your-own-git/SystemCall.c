#include "Definition.h"
#include "io_portfolio_ewhitaker_system_SystemCall.h"

// TODO: abstract

/*
 * Class:     io_portfolio_ewhitaker_system_SystemCall
 * Method:    creat
 * Signature: (Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_io_portfolio_ewhitaker_system_SystemCall_creat(
    JNIEnv *env, jclass clazz, jstring jpathname, jint jmode) {
    long result;

    const char *pathname = (*env)->GetStringUTFChars(env, jpathname, NULL);
    unsigned int mode = jmode;

    register const char *arg1 asm("rdi") = pathname;
    register unsigned int arg2 asm("rsi") = mode;

    asm volatile("syscall\n\t"
                 : "=a"(result)
                 : "0"(SYSCALL_CREAT), "r"(arg1), "r"(arg2)
                 : "memory", "cc", "r11", "cx");

    (*env)->ReleaseStringUTFChars(env, jpathname, pathname);

    return result;
}

/*
 * Class:     io_portfolio_ewhitaker_system_SystemCall
 * Method:    mkdir
 * Signature: (Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_io_portfolio_ewhitaker_system_SystemCall_mkdir(
    JNIEnv *env, jclass clazz, jstring jpathname, jint jmode) {
    long result;

    const char *pathname = (*env)->GetStringUTFChars(env, jpathname, NULL);
    unsigned int mode = jmode;

    register const char *arg1 asm("rdi") = pathname;
    register unsigned int arg2 asm("rsi") = mode;

    asm volatile("syscall\n\t"
                 : "=a"(result)
                 : "0"(SYSCALL_MKDIR), "r"(arg1), "r"(arg2)
                 : "memory", "cc", "r11", "cx");

    (*env)->ReleaseStringUTFChars(env, jpathname, pathname);

    return result;
}
