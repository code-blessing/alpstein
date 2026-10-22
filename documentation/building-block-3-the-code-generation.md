# Building block 3: The code generation

The third building block — the code generation — connects the first two:
[the vertical slice](building-block-1-the-vertical-slice.md) and
[the essential data model](building-block-2-the-essential-data-model.md).

## The vertical slice as template

The vertical slice is **neither pseudocode nor a string template**. It is written by hand,
compiles, runs, and can be refactored in the IDE — exactly the way you would build a feature for
a single entity in an ordinary project.

At the same time, the vertical slice is the **template** from which the code for all other
entities is generated: it shows, by example, what *every* entity should look like in every layer.
The template syntax is "hidden" in source code comments, which the template engine *tavnit*
interprets. These comments mark the spots that differ from entity to entity (names, types, whether
a field is optional or a list, which entities are referenced, ...), for example:

```kotlin
/* @tt{{{
    @foreach [ iteratorExpression="model.attributes" loopVariable="attribute" ]
    @replace-value-by-expression
        [ searchValue="indexUnicus" replaceByExpression="attribute.attributeName.camelCase" ]
        [ searchValue="UUID" replaceByExpression="attribute.kotlinAttributeType" ]
}}}@ */
val indexUnicus: UUID,
/* @tt{{{ @end-foreach }}}@ */
```

As far as the Kotlin/TypeScript compiler is concerned, these are ordinary comments — the code
stays valid and buildable at all times. Only *tavnit* (see below) reads these comments and knows
from them which pieces of text need to be replaced by which value during generation.

For the `Film` item of the essential data model, the `@foreach` above produces one line per
attribute: `val filmId: ...`, `val title: ...`, `val description: ...` and so on, each with the
Kotlin type of that attribute in place of `UUID`.

## The two Gradle modules

The code generation consists of two Gradle modules:

* **`code-generation:renderer-creator`** — at build time, scans the vertical slice (`client`,
  `server:*`, `database:postgresql-dsl-schema`, `documentation`) using *tavnit* and produces
  Kotlin renderer classes from it. From the `@tt{{{ ... }}}@` markers in the vertical slice,
  these classes "know" which pieces of text are variable and how to replace them. The generated renderers land in the `code-generation:code-generator` module under
  `src/tavnit-generated/kotlin`.
* **`code-generation:code-generator`** — while the vertical slice provides the templates, the
  model for these templates comes from `essential-model-data`. The code generator uses
  *SourceAmazing* to read this model into a runtime representation (items, attributes, enums, ...)
  and runs the newly created renderers for **every** item. The result is a complete copy of the
  vertical slice's structure, but with the real names, types, and references from the essential data model.
  It is written into the `generated` directories of the client, the server modules, the database
  migration, and the documentation.

This runs automatically as part of `./gradlew build`: `compileKotlin` in `code-generator` depends
on `createTavnitRenderers` in `renderer-creator`, and the actual `codegen` task then uses the
model and the renderers to write the generated code.

## Where tavnit fits in

**[tavnit](https://github.com/code-blessing/tavnit)** is the template engine and the reason the
vertical slice can be written as ordinary, compiling code instead of a string template. It supplies
two things: the comment syntax (`@tt{{{ ... }}}@`, `@foreach`, `@if`, `@replace-value-by-expression`, ...)
that marks up the vertical slice as a template after the fact, and the renderer generation, which the
`renderer-creator` module runs to translate those markers into executable renderer classes.

In short: *tavnit* turns the vertical slice into a template that can be replayed against the
essential data model for every item.
