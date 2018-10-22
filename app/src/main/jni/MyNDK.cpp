//
// Created by Sneh on 03-01-2017.
//

#include "com_example_newdemo_MyNDK.h"

JNIEXPORT jstring JNICALL Java_com_ispeak_handgesture_MyNDK_getMyString(JNIEnv *env, jobject) {
    return (*env).NewStringUTF("This is NDK Library");
}