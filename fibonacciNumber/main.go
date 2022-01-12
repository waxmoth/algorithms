package main

import (
	"algorithms/fibonacciNumber/solutions"
	"flag"
	"fmt"
	"strconv"
	"time"
)

func main() {
	fibNumber := "20"
	timeout := "5"
	flag.StringVar(&fibNumber, "n", fibNumber, "The fib number. Default is 20.")
	flag.StringVar(&timeout, "t", timeout, "The time out value. Default is 5 seconds.")

	flag.Usage = func() {
		fmt.Printf("Usage: \n")
		fmt.Printf("./main -n fibNumber -t timeout\n")
		flag.PrintDefaults()
	}
	flag.Parse()

	n, _ := strconv.Atoi(fibNumber)
	t, _ := strconv.Atoi(timeout)

	fmt.Printf("solutions.%s(%d) -> %d\n", "FibTwo", n, solutions.FibTwo(n))

	ch := make(chan int)
	callSolution("FibOne", n, ch, t)
	// TODO: Why timeout when call this method?
	callSolution("FibTwo", n, ch, t)
	callSolution("FibThree", n, ch, t)
	defer close(ch)
}

func callSolution(solutionName string, n int, ch chan int, timeout int) {
	go func(name string, n int, ch chan int) {
		switch name {
		case "FibOne":
			ch <- solutions.FibOne(n)
		case "FinTwo":
			ch <- solutions.FibTwo(n)
		case "FibThree":
			ch <- solutions.FibThree(n, make(map[int]int))
		}
	}(solutionName, n, ch)

	select {
	case res := <-ch:
		fmt.Printf("solutions.%s(%d) -> %d\n", solutionName, n, res)
	case <-time.After(time.Duration(timeout) * time.Second):
		fmt.Printf("solutions.%s(%d) -> out of time :(\n", solutionName, n)
	}
}
