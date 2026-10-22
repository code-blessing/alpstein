package org.codeblessing.alpstein.model.builders

/**
 * DslMarker only says:
 * “If multiple implicit receivers share this marker, only the nearest one is visible.” Nothing more. Nothing less.
 *
 * Use different @DslMarkers when you have multiple independent DSL “worlds” that can be nested or used side-by-side,
 * and you don’t want them to hide each other’s receivers.
 *
 * Because Kotlin’s rule is:
 * Only receivers annotated with the same marker participate in hiding outer receivers.
 */
@DslMarker
annotation class MainDslMarker
