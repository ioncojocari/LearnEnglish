#include<jni.h>
#include <stdio.h>
#include<string.h>
/*
jstring getKey(){
jstring  str;
str="fuck the humans is the key!";
return str;
}
*/
void Init( JNIEnv* env )
{
     if( StringBuilder_Class == 0 ) {
         StringBuilder_Class = (*env)->FindClass( env, StringBuilder_ClassName );
         // TODO: Handle error if class not found
     }
     if( StringBuilder_append_Method == 0 ) {
         StringBuilder_append_Method = (*env)->GetMethodID( env, StringBuilder_Class,
             StringBuilder_append_MethodName, StringBuilder_append_MethodSignature );
         // TODO: Handle error if method not found
     }
}
jstring Java_com_example_mrc_learnenglish_MainActivity_getString(JNIEnv* env,jobject obj){
Init();
jstring  str;






return (*env)->NewStringUTF(env,str);

}


