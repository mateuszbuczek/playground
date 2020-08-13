package main

import (
	"bytes"
	"fmt"
	"go/format"
	"html/template"
	"os"
)

type Data struct {
	Name   string
	Desc   string
	Fields []Field
}

type Field struct {
	Name     string
	TypeName string
}

func main() {
	data := []Data{
		{
			Name: "Server",
			Desc: "Details",
			Fields: []Field{
				{
					Name:     "a",
					TypeName: "b",
				},
				{
					Name:     "c",
					TypeName: "d",
				},
			},
		},
		{
			Name: "Server2",
			Desc: "Details2",
			Fields: []Field{
				{
					Name:     "a2",
					TypeName: "b2",
				},
				{
					Name:     "c2",
					TypeName: "d2",
				},
			},
		},
	}
	//str := "Hello, {{.}}"
	//name := "donald"
	//tmpl := template.Must(template.New("custom-name").Parse(str))
	//_ = tmpl.Execute(os.Stdout, name)
	//
	//str = "\n{{ if .}}Hello{{ end }}"
	//tmpl = template.Must(template.New("asd").Parse(str))
	//_ = tmpl.Execute(os.Stdout, 1)
	//
	//names := []string{"Donald", "Bob", "Brodie"}
	//str = "\n{{range . }}Hello, {{ . }}{{ end }} "
	//tmpl = template.Must(template.New("a").Parse(str))
	//_ = tmpl.Execute(os.Stdout, names)
	//
	//str = "\n{{range . }}Hello, {{ toUpper . }}{{ end }} "
	//funcMap := map[string]interface{}{
	//	"toUpper": strings.ToUpper,
	//}
	//tmpl = template.Must(template.New("we").Funcs(funcMap).Parse(str))
	//tmpl.Execute(os.Stdout, names)

	tmple := template.Must(template.New("gf").ParseFiles("str.tmpl"))
	var processed bytes.Buffer
	_ = tmple.Execute(os.Stdout, data)

	formatted, _ := format.Source(processed.Bytes())
	fmt.Println(string(formatted))
}
