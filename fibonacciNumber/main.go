package main

import (
	. "algorithms/fibonacciNumber/solutions"
	"flag"
	"fmt"
	"reflect"
	"runtime"
	"time"
)

// The default timeout for each channel
var timeout = 5

func main() {
	fibNumber := 20
	flag.IntVar(&fibNumber, "n", fibNumber, "The fib number.")
	flag.IntVar(&timeout, "t", timeout, "The timeout settings for each run.")

	flag.Usage = func() {
		fmt.Printf("Usage: \n")
		fmt.Printf("./main -n fibNumber -t timeout\n")
		flag.PrintDefaults()
	}
	flag.Parse()

	callSolution(FibOne, fibNumber)
	callSolution(FibTwo, fibNumber)
	callSolution(FibThree, fibNumber, make(map[int]int))
}

func callSolution(solution interface{}, p ...interface{}) {
	// Get the function from interface
	handler := reflect.ValueOf(solution)
	solutionName := runtime.FuncForPC(handler.Pointer()).Name()
	if handler.Kind() != reflect.Func {
		panic("The solution." + solutionName + " not support yet")
	}

	// Get function parameters
	paramNum := len(p)
	params := make([]reflect.Value, paramNum)
	if paramNum > 0 {
		for k, v := range p {
			params[k] = reflect.ValueOf(v)
		}
	}

	// Async push data into the channel
	ch := make(chan int)
	go func() {
		values := handler.Call(params)
		for _, v := range values {
			ch <- v.Interface().(int)
		}
	}()

	// Get data from the channel
	select {
	case res := <-ch:
		fmt.Printf("%s|solutions.%s(%v) -> %d\n", now(), solutionName, params[0], res)
	case <-time.After(time.Duration(timeout) * time.Second):
		fmt.Printf("%s|solutions.%s(%v) -> out of time :(\n", now(), solutionName, params[0])
	}
	defer close(ch)
}

func now() string {
	return time.Now().Format("2006-01-02 15:04:05.000000")
}
