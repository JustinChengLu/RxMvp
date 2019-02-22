package rx.mvp.compiler.poet

import com.squareup.javapoet.*
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Modifier

/**
 * @author justin on 2019/02/20 10:43
 * @version V1.0
 */

fun javaFile(clazz: ClassName, type: TypeSpec.Builder.() -> Unit): JavaFile.Builder {
    val typeBuilder = TypeSpec.classBuilder(clazz.simpleName())
    type(typeBuilder)

    return JavaFile.builder(clazz.packageName(), typeBuilder.build())
}

fun JavaFile.Builder.generator(processingEnv: ProcessingEnvironment) {
    build().writeTo(processingEnv.filer)
}


fun TypeSpec.Builder.init(block: CodeBlock.Builder.() -> Unit) {
    val builder = CodeBlock.builder()
    block(builder)
    addInitializerBlock(builder.build())
}

fun TypeSpec.Builder.staticInit(block: CodeBlock.Builder.() -> Unit) {
    val builder = CodeBlock.builder()
    block(builder)
    addStaticBlock(builder.build())
}


fun TypeSpec.Builder.field(type: TypeName, name: String, vararg modifiers: Modifier, block: (CodeBlock.Builder.() -> Unit)? = null) {

    val fieldBuilder = FieldSpec.builder(type, name,*modifiers)

    block?.apply {
        val codeBlock = CodeBlock.builder()
        invoke(codeBlock)
        fieldBuilder.initializer(codeBlock.build())
    }
    addField(fieldBuilder.build())
}

fun TypeSpec.Builder.method(name: String, block: MethodSpec.Builder.() -> Unit) {
    val builder = MethodSpec.methodBuilder(name)
    block(builder)
    addMethod(builder.build())
}

fun MethodSpec.Builder.code(block: CodeBlock.Builder.() -> Unit) {
    val builder = CodeBlock.builder()
    block(builder)
    addCode(builder.build())
}

fun CodeBlock.Builder.line(format: String, vararg args: Any) {
    add("$format\n", *args)
}