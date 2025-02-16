#include "Definitions.h"
#include "io_portfolio_ewhitaker_system_SystemCall.h"

/*
 * Class:     io_portfolio_ewhitaker_system_SystemCall
 * Method:    creat
 * Signature: (Ljava/lang/String;J)I
 */
JNIEXPORT jint JNICALL Java_io_portfolio_ewhitaker_system_SystemCall_creat(
    JNIEnv *env, jclass clazz, jstring jpathname, jlong jmode
) {
    INT result;

    STRING pathname = (*env)->GetStringUTFChars(env, jpathname, NULL);
    unsigned int mode = jmode;

    register STRING arg1 asm("rdi") = pathname;
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
 * Signature: (Ljava/lang/String;J)I
 */
JNIEXPORT jint JNICALL Java_io_portfolio_ewhitaker_system_SystemCall_mkdir(
    JNIEnv *env, jclass clazz, jstring jpathname, jlong jmode
) {
    INT result;

    STRING pathname = (*env)->GetStringUTFChars(env, jpathname, NULL);
    unsigned int mode = jmode;

    register STRING arg1 asm("rdi") = pathname;
    register unsigned int arg2 asm("rsi") = mode;

    asm volatile("syscall\n\t"
                 : "=a"(result)
                 : "0"(SYSCALL_MKDIR), "r"(arg1), "r"(arg2)
                 : "memory", "cc", "r11", "cx");

    (*env)->ReleaseStringUTFChars(env, jpathname, pathname);

    return result;
}
