def main():
    data = [
        {"Suma": 150, "Beneficiar": "CasaAlina", "IBAN": "RO0023456", "Descriere": "Restaurant", "Data": "25-05-2023", "Venit": False, "Factura": False},
        {"Suma": 25, "Beneficiar": "Andrei Costin", "IBAN": "RO453456", "Descriere": "Uber", "Data": "15-02-2023", "Venit": False, "Factura": False},
        {"Suma": 6.2, "Beneficiar": "METROREX", "IBAN": "RO44123456", "Descriere": "Transport", "Data": "22-01-2023", "Venit": False, "Factura": False},
        {"Suma": 365.95, "Beneficiar": "MegaImage", "IBAN": "RO04123456", "Descriere": "Cumparaturi alimente", "Data": "12-05-2023", "Venit": False, "Factura": False},
        {"Suma": 125.53, "Beneficiar": "Bershka", "IBAN": "RO04123456", "Descriere": "Cumparaturi haine", "Data": "17-04-2023", "Venit": False, "Factura": False},
        {"Suma": 3500, "Beneficiar": "Vasii Carmen", "IBAN": "RO55123452", "Descriere": "Salariu", "Data": "15-04-2023", "Venit": True, "Factura": False},
        {"Suma": 55.2, "Beneficiar": "CFR", "IBAN": "RO53123456", "Descriere": "Transport CFR", "Data": "09-03-2023", "Venit": False, "Factura": False},
        {"Suma": 100, "Beneficiar": "Vasii Carmen", "IBAN": "RO55123452", "Descriere": "Transfer primt", "Data": "09-06-2023", "Venit": True, "Factura": False},
        {"Suma": 465.22, "Beneficiar": "MegaMall", "IBAN": "RO55123456", "Descriere": "Cumparaturi cadouri", "Data": "29-06-2023", "Venit": False, "Factura": False},
        {"Suma": 32, "Beneficiar": "Matei Popescu", "IBAN": "RO45123456", "Descriere": "Uber", "Data": "19-05-2023", "Venit": False, "Factura": False},
        {"Suma": 24, "Beneficiar": "Dumitru Stefan", "IBAN": "RO11123456", "Descriere": "Bolt", "Data": "24-06-2023", "Venit": False, "Factura": False},
        {"Suma": 25, "Beneficiar": "Vasii Carmen", "IBAN": "RO55123452", "Descriere": "Transfer primit", "Data": "14-05-2023", "Venit": True, "Factura": False},
        {"Suma": 39.89, "Beneficiar": "KebabSocului", "IBAN": "RO55123456", "Descriere": "Restaurant", "Data": "11-03-2023", "Venit": False, "Factura": False},
        {"Suma": 3600, "Beneficiar": "Vasii Carmen", "IBAN": "RO55123452", "Descriere": "Salariu", "Data": "15-03-2023", "Venit": True, "Factura": False},
        {"Suma": 59.4, "Beneficiar": "Vasii Carmen", "IBAN": "RO55123452", "Descriere": "Cadou", "Data": "21-06-2023", "Venit": True, "Factura": False},
        {"Suma": 550, "Beneficiar": "Vasii Carmen", "IBAN": "RO55123452", "Descriere": "Factura telefon", "Data": "21-06-2023", "Venit": False, "Factura": True},
        {"Suma": 520.6, "Beneficiar": "Vasii Carmen", "IBAN": "RO55123452", "Descriere": "Factura enel", "Data": "25-05-2023", "Venit": False, "Factura": True},
        {"Suma": 562, "Beneficiar": "Vasii Carmen", "IBAN": "RO55123452", "Descriere": "Factura enel", "Data": "2-06-2023", "Venit": False, "Factura": True},
        {"Suma": 70, "Beneficiar": "Vasii Carmen", "IBAN": "RO55123452", "Descriere": "Factura enel", "Data": "6-06-2023", "Venit": False, "Factura": True},
        {"Suma": 245, "Beneficiar": "Andrei Matache", "IBAN": "RO0023456", "Descriere": "Chirie", "Data": "25-02-2023", "Venit": False, "Factura": False},
        {"Suma": 300, "Beneficiar": "Andrei Matache", "IBAN": "RO0023456", "Descriere": "Chirie", "Data": "25-03-2023", "Venit": False, "Factura": False},
        {"Suma": 125, "Beneficiar": "Andrei Matache", "IBAN": "RO0023456", "Descriere": "Chirie", "Data": "25-04-2023", "Venit": False, "Factura": False},
        {"Suma": 369.96, "Beneficiar": "Andrei Matache", "IBAN": "RO0023456", "Descriere": "Chirie", "Data": "25-05-2023", "Venit": False, "Factura": False},
        {"Suma": 89.5, "Beneficiar": "Andrei Matache", "IBAN": "RO0023456", "Descriere": "Chirie", "Data": "25-06-2023", "Venit": False, "Factura": False}
    ]

    # Perform data preprocessing
    data_cheltuieli = []
    for entry in data:
        if not entry['Venit']:
            luna = int(entry['Data'].split('-')[1])
            suma = entry['Suma']
            data_cheltuieli.append((luna, suma))

    # Group the expenses by month and calculate the sums
    df_suma_luna = {}
    for luna, suma in data_cheltuieli:
        if luna in df_suma_luna:
            df_suma_luna[luna] += suma
        else:
            df_suma_luna[luna] = suma

    # Train a simple linear regression model
    X = []
    y = []
    for luna, suma in df_suma_luna.items():
        X.append([luna])
        y.append(suma)

    # Compute the coefficients of the linear regression manually
    n = len(X)
    sum_x = sum([x[0] for x in X])
    sum_y = sum(y)
    sum_xy = sum([x[0] * y[i] for i, x in enumerate(X)])
    sum_x_squared = sum([x[0] ** 2 for x in X])

    slope = (n * sum_xy - sum_x * sum_y) / (n * sum_x_squared - sum_x ** 2)
    intercept = (sum_y - slope * sum_x) / n

    # Predict the expenses for the next month
    luna_curenta = max(df_suma_luna.keys())
    luna_viitoare = luna_curenta + 1
    suma_previzionata = slope * luna_viitoare + intercept

    suma_previzionata_rotunjita = round(suma_previzionata, 2)

    return suma_previzionata_rotunjita

previzionare = main()
#print("Previziunea sumei cheltuite în luna următoare:", previzionare)


