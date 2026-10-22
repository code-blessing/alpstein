package org.codeblessing.alpstein.codegen.renderer

class NotSupportedInTemplateException(msg: String)
    : RuntimeException("This data constellation is not supported yet: $msg")
