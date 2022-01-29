package solutions

import (
	"math/rand"
)

type Candidate struct {
	score float64
	item  interface{}
}

type candidateHeap []*Candidate

func (ch *candidateHeap) Pop() Candidate {
	old := *ch
	n := ch.Len()
	candidate := old[n-1]
	old[n-1] = nil
	*ch = old[0 : n-1]
	return *candidate
}

func (ch candidateHeap) Len() int {
	return len(ch)
}

func (ch candidateHeap) Less(i, j int) bool {
	return ch[i].score < ch[j].score
}

func (ch candidateHeap) Swap(i, j int) {
	ch[i], ch[j] = ch[j], ch[i]
}

func (ch *candidateHeap) Push(candidate Candidate) {
	*ch = append(*ch, &candidate)
}

func RandomSelect(candidates []Candidate, limit int) {
	selectedCandidates := make(candidateHeap, 0)
	for _, candidate := range candidates {
		candidate.score = rand.Float64() * candidate.score
		if selectedCandidates.Len() < limit {
			selectedCandidates.Push(candidate)
		} else {
			minScoreCandidate := selectedCandidates.Pop()
			if minScoreCandidate.score < candidate.score {
				selectedCandidates.Push(candidate)
			} else {
				selectedCandidates.Push(minScoreCandidate)
			}
		}
	}
}
