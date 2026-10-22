package org.codeblessing.alpstein.codegen.renderer.model

/**
 * The nil UUID. Rendered as the initial value of a UUID form field, i.e. it marks a
 * UUID that has not been assigned yet (the backend assigns the real one on creation).
 */
const val NIL_UUID: String = "00000000-0000-0000-0000-000000000000"

/** A fixed, well-formed UUID rendered wherever example data for a UUID attribute is needed. */
const val EXAMPLE_UUID: String = "3f2504e0-4f89-11d3-9a0c-0305e82c3301"
