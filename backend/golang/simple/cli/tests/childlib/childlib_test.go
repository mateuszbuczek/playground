package childlib

import "testing"

func Test_MoreBasics(t *testing.T) {
	if 10-5 != 5 {
		t.Error("failed to subtract correctly")
	}
}
