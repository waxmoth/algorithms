package solutions

func FibOne(n int) int {
	if n < 3 {
		return 1
	}

	return FibOne(n-1) + FibOne(n-2)
}
