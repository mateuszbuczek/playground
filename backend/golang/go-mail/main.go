package main

import (
	"fmt"
	"log"
	"mail/email"
	"net/mail"
	"net/smtp"
)

func main() {
	// compose the message
	m := email.NewMessage("Hi", "this is the body")
	m.From = mail.Address{Name: "automation system", Address: "example@gmail.com"}
	m.AddTo("receiver@gmail.com")
	m.AddCc("ccReceived@gmail.com")
	m.AddBcc("bccReceiver@gmail.com")

	// add attachments
	if err := m.Attach("email/email.txt"); err != nil {
		log.Fatal(err)
	}

	// add headers
	m.AddHeader("user-id", "1234")

	// send it
	auth := smtp.PlainAuth("", "example@gmail.com", "passwd", "smtp.gmail.com")
	if err := email.Send("smtp.gmail.com:587", auth, m); err != nil {
		log.Fatal(err)
	}

	fmt.Printf("+%v\n", m.Tolist())
}
