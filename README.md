# SecureBank Automation Test

Selenium + Cucumber (BDD) + TestNG UI test automation framework for the
SecureBank web application, built with the Page Object Model.

## Tech Stack

| Concern              | Library / Tool                                     |
|----------------------|-----------------------------------------------------|
| Browser automation   | Selenium WebDriver 4.x                              |
| BDD / Gherkin        | Cucumber-JVM 7 (`cucumber-java`, `cucumber-testng`) |
| Test runner          | TestNG 7                                            |
| DI for step classes  | cucumber-picocontainer                              |
| Reporting            | Allure (`allure-testng`, `allure-cucumber7-jvm`), Cucumber HTML/JSON |
| Build                | Maven (Java 21)                                     |
| CI                   | GitHub Actions ([.github/workflows/ci.yml](.github/workflows/ci.yml)) |

## Project Structure

```
src/main/java/framework/
├── config/   ConfigManager.java          # loads config-<env>.properties
├── driver/   DriverFactory.java          # creates Chrome/Firefox/Edge WebDriver
│             DriverManager.java          # ThreadLocal driver holder
├── pages/    BaseUI.java, BasePage.java  # Page Object base classes
│             LoginPage.java, BankHomePage.java, PageManager.java
├── components/ BaseComponent.java + SidebarComponent, AccountsComponent,
│               BillPayComponent, DashboardComponent, SendMoneyComponent,
│               TransactionsComponent, TransferComponent, HeaderComponent
└── utils/    JavaScriptUtils.java, ScreenshotUtils.java, WaitUtils.java (placeholders)

src/test/java/
├── config/   TestConfig.java             # testng.xml -> Hooks bridge
├── context/  ScenarioContext.java        # per-scenario PageManager holder
├── hooks/    Hooks.java                  # @Before/@After: driver lifecycle, screenshots
├── model/    User.java                   # login credentials data holder
├── runners/  TestRunner.java             # Cucumber-TestNG entry point
├── steps/    LoginSteps.java             # step definitions
└── types/    DataTableTypes.java, ParameterTypes.java, LoginResult.java

src/test/resources/
├── config-qa.properties                 # base.url, browser, headless, timeout
└── features/login.feature                # Gherkin scenarios

testng.xml                                # cross-browser (Chrome + Firefox) parallel suite
```

Every Page Object (`framework.pages`) and reusable page fragment
(`framework.components`) is documented with a class-level Javadoc comment
explaining its purpose — see the source files for details.

## Configuration

Settings are loaded from `src/test/resources/config-<env>.properties` by
[ConfigManager](src/main/java/framework/config/ConfigManager.java), where
`<env>` comes from the `-Denv` system property (defaults to `qa`, i.e.
`config-qa.properties`).

`browser` and `headless` can be overridden, in priority order:

1. System property (`-Dbrowser=...`, `-Dheadless=...`) — e.g. from Maven/CLI or CI
2. `testng.xml` `<parameter>` value (only used when running via `testng.xml`/`TestRunner`)
3. `config-<env>.properties`

## How Tests Are Run

There are four ways to execute the same underlying Cucumber scenarios. All of
them ultimately go through [Hooks](src/test/java/hooks/Hooks.java) (which
creates/quits the WebDriver) and the step definitions in
[steps/](src/test/java/steps).

### 1. Run directly from a `.feature` file (IDE)

Requires the **Cucumber for Java** plugin (IntelliJ IDEA). Open a
`.feature` file (e.g.
[login.feature](src/test/resources/features/login.feature)) and click the
green ▶ gutter icon next to:

- the `Feature:` line — runs every scenario in that file
- a `Scenario:` / `Scenario Outline:` line — runs just that scenario
- an `Examples:` row — runs just that example

The first time you do this, IntelliJ asks you to configure the run
configuration template under **Run → Edit Configurations → Cucumber java**:
set **Glue** to `steps hooks types` (matching `@CucumberOptions.glue` in
[TestRunner](src/test/java/runners/TestRunner.java)) so step definitions are
found. Because this bypasses TestNG/`testng.xml`, `browser`/`headless` fall
back to `config-qa.properties` unless you add `-Dbrowser=... -Dheadless=...`
as VM options in the run configuration.

### 2. Run from the `TestRunner` class (IDE)

Right-click [TestRunner.java](src/test/java/runners/TestRunner.java) →
**Run 'TestRunner'**. Since it extends `AbstractTestNGCucumberTests`, TestNG
discovers and runs every scenario under
`src/test/resources/features` as its own test. This is the same class
`testng.xml` and `mvn test` use under the hood, so it's a quick way to run
the full suite (or a subset, via `@CucumberOptions(tags = "...")` /
`-Dcucumber.filter.tags=...` as a VM option) without the TestNG/Maven layer.

### 3. Run via `testng.xml`

[testng.xml](testng.xml) defines a parallel, cross-browser suite: it runs
[TestRunner](src/test/java/runners/TestRunner.java) twice, once per
`<test>` block, each with its own `browser`/`headless` `<parameter>`
(Chrome and Firefox, both headless) picked up by
`TestRunner.configureExecution` and stored in
[TestConfig](src/test/java/config/TestConfig.java) for `Hooks` to read.

- **IDE:** right-click `testng.xml` → **Run**.
- **Command line / TestNG directly:** build the classpath and invoke
  TestNG's CLI with the suite file, e.g. using the TestNG Ant task or:
  ```bash
  mvn dependency:build-classpath -Dmdep.outputFile=cp.txt
  java -cp "target/classes:target/test-classes:$(cat cp.txt)" org.testng.TestNG testng.xml
  ```
- **Via Maven:** the `<suiteXmlFiles>` block in
  [pom.xml](pom.xml)'s `maven-surefire-plugin` config is currently commented
  out, so `mvn test` does **not** use `testng.xml` by default (see next
  section). Uncomment it if you want `mvn test` to run this suite instead of
  the single default `TestRunner` class.

### 4. Run via Maven (`mvn`)

Because `TestRunner.java` matches Surefire's default `Test*.java` include
pattern, running Maven picks it up automatically — no `testng.xml` needed.

**Run everything:**
```bash
mvn test
```

**Run with a specific browser / headless mode:**
```bash
mvn test -Dbrowser=firefox -Dheadless=true
```

**Run by Cucumber tag** (e.g. only `@smoke`, or a boolean expression):
```bash
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@regression and not @negative"
```

**Run per feature** — restrict execution to a single `.feature` file (or a
folder of feature files), overriding the `features` path from
`@CucumberOptions`:
```bash
mvn test -Dcucumber.features="src/test/resources/features/login.feature"
```

**Run per scenario** — yes, this is possible two ways:

- By **line number** (append `:<line>` to the feature path; the line of a
  `Scenario:`/`Scenario Outline:` or an `Examples:` row runs just that
  scenario/example):
  ```bash
  mvn test -Dcucumber.features="src/test/resources/features/login.feature:9"
  ```
- By **scenario name** (regex match against the scenario title, works across
  all discovered features):
  ```bash
  mvn test -Dcucumber.filter.name="Successful login"
  ```

These flags can be combined, e.g. run one feature file, one browser, headless:
```bash
mvn test -Dcucumber.features="src/test/resources/features/login.feature" \
         -Dbrowser=chrome -Dheadless=true
```

## Reports

- **Cucumber:** `target/cucumber-report.html`, `target/cucumber.json`
- **Allure:** results in `target/allure-results`; generate/view the HTML
  report with:
  ```bash
  mvn allure:report   # static report in target/site/allure-maven-plugin
  mvn allure:serve    # builds and opens the report in a browser
  ```

## Continuous Integration

### GitHub Actions

[.github/workflows/ci.yml](.github/workflows/ci.yml) runs the suite on
GitHub Actions via manual dispatch, letting you pick `tags`, `browser`
(`both`/`chrome`/`firefox`, run as a matrix), and `headless`, then runs
`mvn clean test -Dbrowser=... -Dheadless=... -Dcucumber.filter.tags=...` and
uploads the Allure and Cucumber reports as build artifacts.

### Jenkins

[Jenkinsfile](Jenkinsfile) runs the same underlying suite through two extra
TestNG suite files purpose-built for CI parallelism:

- [testng-parallel.xml](src/test/resources/testng-parallel.xml) — one
  browser, `parallel="methods" thread-count="4"`: runs Cucumber **scenarios**
  concurrently within a single browser.
- [testng-crossbrowser.xml](src/test/resources/testng-crossbrowser.xml) —
  `parallel="tests" thread-count="3"` across three `<test>` blocks
  (Chrome/Firefox/Edge): runs the **same** scenarios on all three browsers
  concurrently.
- [testng.xml](testng.xml) (repo root) — the original two-browser
  (Chrome/Firefox) suite, kept as the default for local/manual runs when no
  suite is explicitly selected.

Job parameters: `EXECUTION_TYPE` (`parallel-same-browser` / `cross-browser` /
`both`, run as parallel Jenkins stages), `BROWSER` (for the same-browser
mode), `TAGS` (Cucumber tag expression, e.g. `@ui`), `THREADS` (thread count
for the same-browser mode).

Reports: the `Publish Reports` stage runs `publishHTML` for the Cucumber
report (requires the **HTML Publisher** plugin) and the `allure` step for
the Allure report (requires the **Allure Jenkins Plugin**), then archives
`target/allure-results/**`, `target/cucumber-report.html` and
`target/cucumber.json` as build artifacts. `mvn allure:report`/`allure:serve`
are **not** used in CI — see "Gotchas" below for why.

#### Gotchas this Jenkinsfile/pom.xml had to work around

These aren't just style choices — each one silently produced wrong or
corrupted results before being fixed, so they're documented here to avoid
regressing them:

1. **`suiteXmlFiles` has no command-line override.** Maven Surefire's
   `suiteXmlFiles` parameter has no bound `-D` property at all (its own docs
   list `User Property: -`) — passing `-Dsurefire.suiteXmlFiles=...` or any
   other `-D` variant on the CLI is silently accepted by the JVM as an inert
   system property that nothing ever reads; it has **zero effect** on which
   suite actually runs. **Fix:** [pom.xml](pom.xml) defines a plain Maven
   property, `<suiteXmlFile>testng.xml</suiteXmlFile>`, and the plugin
   config references it as `<suiteXmlFile>${suiteXmlFile}</suiteXmlFile>`.
   Regular Maven properties (unlike plugin `@Parameter`s) *are* overridable
   from the CLI, so `-DsuiteXmlFile=src/test/resources/testng-parallel.xml`
   now genuinely selects a different suite; `mvn test` with no override
   still defaults to `testng.xml`.
2. **Wrong TestNG `parallel` mode.** [testng-parallel.xml](src/test/resources/testng-parallel.xml)
   had `parallel="tests"` with only a single `<test>` block — that mode only
   parallelizes across separate `<test>` tags, so with just one, nothing
   actually ran concurrently despite `thread-count="4"` being set. **Fix:**
   changed to `parallel="methods"`, which parallelizes at the individual
   test-method (i.e. Cucumber scenario) level instead.
3. **Suite-level `parallel`/`thread-count` alone doesn't parallelize Cucumber
   scenarios.** `cucumber-testng` enumerates scenarios via a `scenarios()`
   `@DataProvider`; TestNG only runs a data provider's invocations
   concurrently if the provider itself is declared `parallel = true` —
   otherwise it runs them sequentially no matter what the suite's `parallel`/
   `thread-count` say. **Fix:** [TestRunner](src/test/java/runners/TestRunner.java)
   now overrides `scenarios()` as `@Override @DataProvider(parallel = true)`.
   Safe here since `DriverManager`/`TestConfig` are both `ThreadLocal`.
4. **Shared workspace across parallel Jenkins branches.** The pipeline
   originally had `agent any` at the top level, so both parallel branches
   shared one workspace and ran `mvn clean test` concurrently in it — one
   branch's `clean` could delete `target/` (results, screenshots, reports)
   while the other branch was still writing to it. **Fix:** `agent none` at
   the pipeline level, with each branch (and the `Publish Reports` stage)
   declaring its own `agent any` + `checkout scm`, giving each an isolated
   workspace; results are handed to `Publish Reports` via Jenkins `stash`/
   `unstash` rather than a shared directory.
5. **`-DthreadCount` is a no-op once you supply your own suite file** — it
   only affects a suite Surefire auto-generates for you, not one you pass in
   via `suiteXmlFile`. **Fix:** the `Same Browser` branch `sed`-rewrites the
   `thread-count` attribute into a generated copy of `testng-parallel.xml`
   before invoking Maven, so the `THREADS` Jenkins parameter has a real
   effect. The generated file is written outside `target/` so `mvn clean`
   doesn't delete it before it's used.
6. **`mvn allure:report` / `mvn allure:serve` don't work well in CI.**
   `allure:serve` starts a blocking local dev server and tries to open a
   browser — meaningless on a headless agent, and it would hang the build.
   `allure:report`'s static HTML also relies on AJAX-loading JSON, which can
   fail when opened via `file://` rather than `http://`. **Fix:** the
   pipeline never calls either goal; the **Allure Jenkins Plugin**'s `allure`
   step reads the raw `target/allure-results` JSON directly and renders/
   serves the report through Jenkins' own web server instead, with build-
   over-build trend history as a bonus.
