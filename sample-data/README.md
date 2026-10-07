# Sample data

Used by the project increments and by the instructor demos. Every file here was
run through the reference implementation; the outcomes below are real output,
not expectations.

| File                   | What it exercises              | Result against `merchant-settlement`                                                                                |
|------------------------|--------------------------------|---------------------------------------------------------------------------------------------------------------------|
| `valid.csv`            | the happy path                 | 3 valid rows, 0 errors                                                                                              |
| `missing-required.csv` | required-field rule            | 1 valid, 2 errors (empty `merchantId`, empty `amount`)                                                              |
| `too-long.csv`         | max-length rule                | 1 valid, 1 error (`transactionId` is 16 chars, max 12)                                                              |
| `quoted-comma.csv`     | the naive-`split(",")` trap    | 2 valid, 0 errors **with a real CSV reader**; a hand-rolled `split(",")` sees 5 columns and wrongly rejects the row |
| `wrong-columns.csv`    | column-count mismatch          | 0 valid, 1 error ("expected 4 columns but found 2")                                                                 |
| `settlement.xlsx`      | the Excel path (checkpoint 09) | 2 valid, 2 errors                                                                                                   |

`../templates.json` holds the `merchant-settlement` template these are validated against.
