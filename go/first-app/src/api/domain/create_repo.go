package domain

type CreateRepoRequest struct {
	Name        string `json:"name"`
	Description string `json:"description"`
	Homepage    string `json:"homepage"`
	Private     bool   `json:"private"`
	HasIssues   bool   `json:"has_issues"`
	HasProjects bool   `json:"has_projects"`
	HasWiki     bool   `json:"has_wiki"`
}

type CreateRepoResponse struct {
	Id          int64                         `json:"id"`
	Name        string                        `json:"name"`
	FullName    string                        `json:"full_name"`
	Owner       CreateRepoResponseRepoOwner   `json:"owner"`
	Permissions CreateRepoResponsePermissions `json:"permissions"`
}

type CreateRepoResponseRepoOwner struct {
	Id      int64  `json:"id"`
	Login   string `json:"login"`
	Url     string `json:"url"`
	HtmlUrl string `json:"html_url"`
}

type CreateRepoResponsePermissions struct {
	IsAdmin bool `json:"is_admin"`
	HasPull bool `json:"has_pull"`
	HasPush bool `json:"has_push"`
}

type ErrorResponse struct {
	StatusCode       int                 `json:"status_code"`
	Message          string              `json:"message"`
	DocumentationUrl string              `json:"documentation_url"`
	Errors           []ErrorResponseItem `json:"errors"`
}

type ErrorResponseItem struct {
	Resource string `json:"resource"`
	Code     string `json:"code"`
	Field    string `json:"field"`
	Message  string `json:"message"`
}
