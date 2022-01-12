package solutions

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
