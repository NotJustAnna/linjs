@file:OptIn(ExperimentalJsExport::class)

import net.notjustanna.lin.ast.node.Node
import net.notjustanna.lin.compiler.NodeCompiler
import net.notjustanna.lin.validator.NodeValidator

@JsExport
class ParsedSnippet internal constructor(private val node: Node) {
    fun validate(): ValidationReport {
        return ValidationReport(node.accept(NodeValidator))
    }

    fun compile(): TimedResult<CompiledSnippet> {
        return measureTimedRunCatching { NodeCompiler.compile(node).jsWrapped }
    }
}

internal inline val Node.jsWrapped get() = ParsedSnippet(this)
