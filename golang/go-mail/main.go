package main

import (
	"log"
	"mail/email"
	"net/mail"
	"net/smtp"
)

func main() {
	// compose the message
	m := email.NewMessage("Hi", "this is the body")
	m.From = mail.Address{Name: "From", Address: "from@example.com"}
	m.AddTo(mail.Address{Name: "someToName", Address: "to@example.com"})
	m.AddCc(mail.Address{Name: "someCcName", Address: "cc@example.com"})
	m.AddBcc(mail.Address{Name: "someBccName", Address: "bcc@example.com"})

	// add attachments
	if err := m.Attach("email/email.go"); err != nil {
		log.Fatal(err)
	}

	// add headers
	m.AddHeader("X-CUSTOMER-id", "xxxxx")

	// send it
	auth := smtp.PlainAuth("asd", "from@example.com", "pwd", "smtp.zoho.com")
	if err := email.Send("smtp.zoho.com:587", auth, m); err != nil {
		log.Fatal(err)
	}
}
