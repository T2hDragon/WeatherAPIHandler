## Theoretical part


### 1. Which of the following activities cannot be automated
- [ ] Test execution
- [x] Exploratory testing
- [ ] Discussing testability issues
- [ ] Test data generation

### 2. How do we describe a good unit test?
- [ ] Flawless, Ready, Self-healing, True, Irresistible
- [ ] Red, Green, Refactor
- [x] Fast, Repeatable, Self-validating, Timely, Isolated
- [ ] Tests should be dependent on other tests

### 3. When is it a good idea to use XPath selectors
- [ ] When CSS or other selectors are not an option or would be brittle and hard to maintain
- [ ] When we need to find an element based on parent/child/sibling relationship
- [ ] When an element is located deep within the HTML (or DOM) structure
- [x] All the above

### 4. Describe the TDD process
1. Write tests
2. Look, how it fails
3. Write enough code for it to work
4. Refactor code
5. Go to step 1

### 5. Write 2 test cases or scenarios for a String Calculator application, which takes a string of two numbers separated by a comma as input.
**Scenario:** Should calculate sum\
&nbsp;&nbsp;**Given** the input "-6,5"\
&nbsp;&nbsp;**When** the method ```calculate()``` is called\
&nbsp;&nbsp;**Then** I should see "-1" as a result.\
\
**Scenario:** Should take empty string as zero\
&nbsp;&nbsp;**Given** the input ","\
&nbsp;&nbsp;**When** the method ```calculate()``` is called\
&nbsp;&nbsp;**Then** I should see "0" as a result.  
