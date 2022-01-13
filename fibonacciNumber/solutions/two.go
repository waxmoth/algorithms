package solutions

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
