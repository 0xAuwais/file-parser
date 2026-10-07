# File Parser

A multi-day exercise: build a file parser (fixed-length, CSV, and eventually Excel) increment by
increment.

```
file-parser/
  pom.xml             parent POM — module list and pinned dependency versions
  file-parser-core/   the parsing/validation library
  file-parser-cli/    the CLI that drives it
  sample-data/        valid and deliberately-broken files, plus an .xlsx
```

`mvn clean package` from the repo root should succeed before you write a line of Java. Get that
green first.

## Branching workflow

Each trainee has their own branch named after them (e.g. `khaled-alkarmy`).

1. Clone the project.
2. Checkout your own named branch — treat it as **your personal `master`**: it's the branch all
   your task branches come from and merge back into.
3. For each task/increment, create a new branch off your own branch, named after the task
   (e.g. `khaled-alkarmy/increment-05-jackson`).
4. Do the work on that task branch, commit, and push it.
5. Open a Marge request to merge the task branch back into your own named branch.

```bash
git clone <repo-url>
cd file-parser
git checkout khaled-alkarmy          # your own branch — your "master"
git checkout -b khaled-alkarmy/increment-05-jackson
# ... do the work, commit ...
git push -u origin khaled-alkarmy/increment-05-jackson
# then open a PR: khaled-alkarmy/increment-05-jackson -> khaled-alkarmy
```

`master` and every trainee's own branch are **protected**: nobody can push to them directly.
All changes land through a marge request and review, never a direct push.

## What's in the parent POM, and what isn't

It gives you **dependency versions** in `<dependencyManagement>` — so when an increment says "add
Jackson", you add three lines with no `<version>` and no trip to Maven Central:

```xml
<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-databind</artifactId>
</dependency>
```

It does **not** add any of them to a classpath. `<dependencyManagement>` only sets the version to
use *if* something declares the dependency. You add each one to the module that needs it, when the
increment tells you to.

It also does not write your module POMs. Those are yours.

## templates.json

Created from increment 05 onward, when your CLI starts writing it. One worked example to start
from:

```json
{
  "templates": [
    {
      "name": "merchant-settlement",
      "fields": [
        { "name": "transactionId", "length": 12, "required": true },
        { "name": "merchantId",    "length": 8,  "required": true },
        { "name": "amount",        "length": 15, "required": true },
        { "name": "reference",     "length": 30, "required": false }
      ]
    }
  ]
}
```

`length` is the **maximum** allowed characters. As you add and edit templates through the CLI, this
file changes with them.

## sample-data/

Six files, each exercising something specific — see `sample-data/README.md` for what each one
should produce. Use them from increment 02 onward; they're how you know your validation is right
rather than merely running.

`settlement.xlsx` won't work until increment 09, when Excel support arrives. That's expected.
