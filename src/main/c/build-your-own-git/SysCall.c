#include <stdio.h>

#include "io_portfolio_ewhitaker_SysCall.h"

JNIEXPORT void JNICALL Java_io_portfolio_ewhitaker_SysCall_hello(JNIEnv *env,
                                                                 jclass clazz) {
  printf("hello world\n");
}
