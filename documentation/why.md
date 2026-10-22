# Why Alpstein?

## How did this project come about?

Besides the genuinely valuable and interesting data entities that are used every day, almost every software project also has
a large number of boring data entities that rarely or never change: code tables, categories, country
lists, VAT rate tables, postal-code-to-city mappings, etc.
Over my years as a software developer, I have had to write — or copy-and-modify — similar but never quite
identical code for exactly these entities in every single project: similar repositories, similar
REST endpoints, similar UI components and so on.
These entities in particular should require as little effort as possible, yet you still want to be
able to display and maintain them properly.

## Which decisions were made along the way?

Broadly speaking, I see two ways to solve this problem:

1. A framework that handles arbitrary entities dynamically (= at runtime) and provides a single UI
   form, a single server endpoint, etc. that are dynamic enough to display any requested entity.
2. Generating code for all entities statically (= at compile/build time).

The two directions have the following consequences:

- A dynamic framework ties you very tightly to a specific stack, i.e. to specific programming
  languages, frameworks and libraries. Changing this afterwards tends to be an expensive endeavour.
- A dynamic framework is less transparent, since it is a black box. You may be able to step through
  it with a debugger, but unlike generated code, it is not your own code.
- Generated code is rigid at runtime. Changing certain things at runtime is difficult or impossible.
  One example is a presentation that differs per logged-in user (e.g. which data is shown).
  Since the code is generated once for all users and not per logged-in user, an interface
  cannot simply send only the data the logged-in user is actually supposed to see.
- In larger projects, generated code can produce a very large number of files. Generating them is
  usually not the problem — compiling all of them may be.

This example project follows the second direction: static code generation.

Within code generation, I again see two directions:

1. A separate code generation framework for each aspect ("best-of-breed").
2. The same code generation framework for every aspect ("one-size-fits-all").

Here, "aspect" means generating database classes, migration scripts, OpenAPI specifications,
documentation, frontend components, interface integrations, etc.

The main advantages of the "best-of-breed" approach:

- You can work with existing, mature open-source frameworks that others have already written. They
  only need to be integrated into the existing build process, and well-known frameworks come with
  plenty of experienced people and forums.

These are offset by the following disadvantages:

- Each of these code generation frameworks has to be understood, integrated and maintained
  (updates, etc.) individually.
- Each of these code generation frameworks needs its own input model (i.e. a definition of what is
  to be generated). This usually leads to many redundant input models.
- Code generation frameworks can usually only be adapted to your own needs to a limited extent.
  This usually leads to many compromises in the generated code.
- The outputs of two code generation frameworks need adapter classes in between. These are not
  generated but have to be written by hand.

The "one-size-fits-all" approach is the mirror image: the disadvantages above become its
advantages, and the advantages become its disadvantages.

This example project follows the second, "one-size-fits-all" direction. In Alpstein, that one
framework is the combination of a handwritten vertical slice and the template engine *tavnit*,
which turns the slice into the template for every layer. The
[three building blocks](building-blocks.md) describe this in detail.

## What does this approach give you?

The advantages below refer to the three building blocks: the vertical slice, the essential data
model and the code generation.

* **The cost of introducing a new entity drops dramatically**: no matter how insignificant an entity is, it is
  implemented and maintained properly.
* **DRY across all entities and all layers**: a pattern (e.g. "what an editor form looks
  like") is written exactly once — in the vertical slice — instead of being rebuilt or copied by
  hand for every item.
* **Code generation on every layer from day one**: redundancy and boilerplate can be avoided
  wherever they occur — not only on the layers for which a suitable tool happens to exist.
* **Full tooling support while designing the template**: the vertical slice is ordinary
  Kotlin/TypeScript code. When you design a new feature, you get the compiler, type checking, the IDE
  and, in principle, the ability to run it — not only after generation.
* **Domain model and technical implementation are decoupled**: the essential data model describes
  only *what* the project is about; the vertical slice describes only *how* it is technically
  implemented. Either can change independently of the other.
* **Consistency across all entities**: since every entity is produced from the same template, all
  search boards, editors, and repositories behave identically by construction — deviations can't
  creep in through copy-paste mistakes or human preferences.
* **Changes to a pattern take effect everywhere at once**: giving every editor form a new UI
  element only requires a change to the vertical slice plus a re-generation run — instead of editing hundreds of
  files by hand or refactoring them gradually over a long period of time (following the "Boy Scout Rule").
* **Generated code needs neither refactoring nor its own tests**: it changes together with the vertical slice,
  and if the vertical slice is well tested, so is the code generated from it.
* **Documentation stays in sync**: the ERD and all other documentation are generated from the same model, so it
  cannot drift from the actual data model.
* **Development focuses on the vertical slice**: both for software architects designing new
  features and for newcomers getting to know the project.
* **Leaner reviews, no merge conflicts**: the generated code is not checked into the repository,
  so thousands of files never need to be reviewed and cannot cause merge conflicts. Reviews can
  focus on what was actually written by hand: the vertical slice and the essential data model.
* **Less context for AI-assisted development**: when developing with AI (Claude, Codex, etc.), the
  AI can focus exclusively on the vertical slice. This saves context — for the AI, and for the person reading along.
