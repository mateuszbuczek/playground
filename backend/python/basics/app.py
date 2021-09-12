import os.path

def getCustomer(customerId):
    return 'customer-test' + customerId

print(getCustomer('1'))

customers = ['a', 'b']
customers.append('c')

print(customers)
print(len(customers))

# class
class Customer:
    def __init__(self, c='', f='', l=''):
        self.customerId = c
        self.firstName = f
        self.lastName = l
    def fullName(self):
        return self.firstName + ' ' + self.lastName

test1 = Customer('1', 'a', 'b')
print(test1.fullName())

