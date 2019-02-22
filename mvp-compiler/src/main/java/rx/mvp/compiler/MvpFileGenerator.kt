package rx.mvp.compiler

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import rx.mvp.annotation.Contract
import rx.mvp.compiler.poet.*
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

/**
 * @author justin on 2019/02/22 10:33
 * @version V1.0
 */

class MvpFileGenerator(private val processingEnv: ProcessingEnvironment) {
    private val mvpBindingClassName = ClassName.get("rx.mvp", "MvpBinding")
    private val contracts = ArrayList<MvpContract>()
    fun process(env: RoundEnvironment) {
        @Suppress("UNCHECKED_CAST")
        val all = (env.rootElements as Set<TypeElement>)
                .filter { !it.toString().startsWith("android.") && it.interfaces.isNotEmpty() }
        val temp = env.getElementsAnnotatedWith(Contract::class.java)
                .map {
                    Triple(it.enclosedElements.find { it.getAnnotation(Contract.Model::class.java) != null },
                            it.enclosedElements.find { it.getAnnotation(Contract.View::class.java) != null }!!,
                            it.enclosedElements.find { it.getAnnotation(Contract.Presenter::class.java) != null }!!)
                }
                .map { contract ->
                    MvpContract(contract.first?.run { all.first { it.interfaces.contains(this.asType()) } },
                            all.first { it.interfaces.contains(contract.second.asType()) },
                            all.first { it.interfaces.contains(contract.third.asType()) })
                }
        contracts.addAll(temp)
        if (env.processingOver()) {
            val t = contracts.distinctBy { it.v.toString() }
            t.forEach(this::generatorMvpContract)
            generatorMvpData(t)
        }
    }

    private fun generatorMvpContract(contract: MvpContract) {
        val name = buildMvpContractName(contract.v)

        javaFile(name) {
            addSuperinterface(mvpBindingClassName)
            addModifiers(Modifier.PUBLIC)
            method("bind") {
                addModifiers(Modifier.PUBLIC)
                addAnnotation(Override::class.java)
                addParameter(ClassName.get(Any::class.java), "v")
                code {
                    line("\$T view = (\$T)v;", contract.v, contract.v)
                    if (contract.m != null) {
                        line("new \$T(view,new \$T());", contract.p, contract.m)
                    } else {
                        line("new \$T(view);", contract.p)
                    }
                }
            }
        }.generator(processingEnv)
    }

    private fun generatorMvpData(contracts: List<MvpContract>) {

        val map = ParameterizedTypeName.get(ClassName.get(HashMap::class.java), ClassName.get(Class::class.java), mvpBindingClassName)
        javaFile(ClassName.get("rx.mvp", "BindingData")) {
            field(map, "data", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL) {
                add("new \$T()", map)
            }
            staticInit {
                contracts.forEach {
                    val name = buildMvpContractName(it.v)
                    line("data.put(\$T.class,new \$T());", it.v, name)
                }
            }
        }.generator(processingEnv)
    }

    private fun buildMvpContractName(element: TypeElement): ClassName {
        val activityClassName = ClassName.get(element)

        val packageName = activityClassName.packageName()
        val className = element.qualifiedName.toString().substring(packageName.length + 1).replace('.', '$')
        return ClassName.get(packageName, className + "_MvpBinding")
    }

//    private fun log(message: Any?) {
//        processingEnv.messager.printMessage(Diagnostic.Kind.OTHER, message.toString())
//    }


    data class MvpContract(val m: TypeElement? = null, val v: TypeElement, val p: TypeElement)
}