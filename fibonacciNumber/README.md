## Problem
Mathematician Leonardo Fibonacci posed this problem:
"How many pairs of rabbits will be produced in a year, beginning with a single pair, if in every month each pair bears a new pair which becomes productive from the second month on?"
The function for this problem is:
```
IF n < 3:
     F(n) = 1
ELSE:
     F(n) = F(n-1) + F(n-2)
```

## Solutions
1. A simple recursive algorithm.
```golang
func FibOne(n int) int {
	if n < 3 {
		return 1
	}

	return FibOne(n-1) + FibOne(n-2)
}
```

Benchmark this solution, run 10 times to calculate Fibonacci number 10
```bash
go test -v ./tests/ -bench=BenchmarkFibOne -count=10 -run=none
```

The output is similar as:
```
goos: linux
goarch: amd64
pkg: algorithms/fibonacciNumber/tests
cpu: AMD Ryzen 7 3800X 8-Core Processor             
BenchmarkFibOne
BenchmarkFibOne-16       7761829               152.8 ns/op
BenchmarkFibOne-16       7763792               156.4 ns/op
BenchmarkFibOne-16       7463102               150.8 ns/op
... ...
PASS
ok      algorithms/fibonacciNumber/tests        13.352s
```

If calculate the value for a large number, e.g. 50. This method needs a lot of time to finish the job.

2. Calculate the value from 1 to n
```golang
func FibTwo(n int) int {
	if n < 3 {
		return 1
	}

	s, l := 1, 1
	for i := 1; i < n; i++ {
		s, l = l, s+l
	}
	return s
}
```

Benchmark this solution, run 10 times to calculate Fibonacci number 10
```bash
go test -v ./tests/ -bench=BenchmarkFibTwo -count=10 -run=none
```

The output is similar as:
```
goos: linux
goarch: amd64
pkg: algorithms/fibonacciNumber/tests
cpu: AMD Ryzen 7 3800X 8-Core Processor             
BenchmarkFibTwo
BenchmarkFibTwo-16      324590876                3.611 ns/op
BenchmarkFibTwo-16      329934450                3.700 ns/op
BenchmarkFibTwo-16      338031040                3.476 ns/op
... ...
PASS
ok      algorithms/fibonacciNumber/tests        15.656s
```

3. Cache the fib numbers into memory then reuse it
```golang
func FibThree(n int, fibs map[int]int) int {
	if n < 3 {
		return 1
	}

	fib, exists := fibs[n]
	if exists == false {
		fibs[n] = FibThree(n-1, fibs) + FibThree(n-2, fibs)
		fib = fibs[n]
	}

	return fib
}
```

Benchmark this solution, run 10 times to calculate Fibonacci number 10
```bash
go test -v ./tests/ -bench=BenchmarkFibThree -count=10 -run=none
```

The output is similar as:
```
goos: linux
goarch: amd64
pkg: algorithms/fibonacciNumber/tests
cpu: AMD Ryzen 7 3800X 8-Core Processor
BenchmarkFibThree
BenchmarkFibThree-16             6066397               197.7 ns/op
BenchmarkFibThree-16             5969319               200.4 ns/op
BenchmarkFibThree-16             5954078               194.4 ns/op
... ...
PASS
ok      algorithms/fibonacciNumber/tests        13.774s
```
