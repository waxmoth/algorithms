package tests

import (
	"algorithms/fibonacciNumber/solutions"
	"testing"
)

func BenchmarkFibOne(b *testing.B) {
	for i := 0; i < b.N; i++ {
		solutions.FibOne(10)
	}
}

func TestFibOne(t *testing.T) {
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
	}

	for i, tc := range testCases {
		result := solutions.FibOne(tc.n)
		if result != tc.expected {
			t.Fatalf("Test %d failed — Expected %d, got %d", i+1, tc.expected, result)
		}
	}
}
