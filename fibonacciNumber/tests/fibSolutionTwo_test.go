package tests

import (
	"algorithms/fibonacciNumber/solutions"
	"testing"
)

func BenchmarkFibTwo(b *testing.B) {
	for i := 0; i < b.N; i++ {
		solutions.FibTwo(10)
	}
}

func TestFibTwo(t *testing.T) {
	testCases := []struct {
		n        int
		expected int
	}{
		{n: 1, expected: 1},
		{n: 2, expected: 1},
		{n: 3, expected: 2},
		{n: 10, expected: 55},
		{n: 18, expected: 2584},
		{n: 20, expected: 6765},
		{n: 50, expected: 12586269025},
		{n: 100, expected: 3736710778780434371},
	}

	for i, tc := range testCases {
		result := solutions.FibTwo(tc.n)
		if result != tc.expected {
			t.Fatalf("Test %d failed — Expected %d, got %d", i+1, tc.expected, result)
		}
	}
}
