package sv

import "fmt"

type SemanticVersion struct {
	major, minor, patch int
}

func NewSemanticVersion(major, minor, patch int) SemanticVersion {
	return SemanticVersion{
		major: major,
		minor: minor,
		patch: patch,
	}
}

// pointer based receiver - this func will use pointer instead of copy of object
func (sv *SemanticVersion) ToString() string {
	return fmt.Sprintf("%d.%d.%d", sv.major, sv.minor, sv.patch)
}

func (sv *SemanticVersion) IncrementMajor() {
	sv.major += 1
}
