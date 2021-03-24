package main

import "fmt"

type intSlice []int

func main() {
	intList := intSlice{3, 5, 7, 8, 9, 11, 13, 17, 24, 55, 58, 65, 88, 99}

	searchNumber := 11

	result := -1

	if searchNumber >= intList[0] && searchNumber <= intList[len(intList)-1] {
		result = binary(intList, 0, len(intList)-1, searchNumber)
	}

	if result == -1 {
		fmt.Println("Not found")
	} else {
		fmt.Println("Number found")
	}
}

func binary(arr []int, left int, right int, num int) int {
	if right >= left {
		mid := (left + right) / 2

		if arr[mid] == mid {
			return mid
		}

		if arr[mid] > num {
			return binary(arr, left, mid-1, num)
		} else {
			return binary(arr, mid+1, right, num)
		}
	}

	return -1
}
