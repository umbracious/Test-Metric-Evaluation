import pandas as pd
import statistics
import matplotlib.pyplot as plt
from scipy import stats


# Lecture CSV file 
file_path = 'jfreechart-test-stats.csv'
# Pour separer les columns
df = pd.read_csv(file_path, delimiter=',', skiprows=1, header=None)

# Column names 
column_names = ['class', 'TLOC', 'WMC', 'TASSERT']  # Adjust based on your actual column names

df.columns = column_names

# Ajoute tout les TLOC, WMC, TASSERT dans leurs array assign
tloc_values = df['TLOC'].tolist()
wmc_values = df['WMC'].tolist()
tassert_values = df['TASSERT'].tolist()

# Avec l'aide de statistics calcule la mediane et la retourne
tloc_mediane = str(statistics.median(tloc_values))

wmc_mediane = str(statistics.median(wmc_values))

tassert_mediane = str(statistics.median(tassert_values))

print("mediane pour TLOC : " + tloc_mediane)

print("mediane pour WMC : " + wmc_mediane)

print("mediane pour TASSERT : " + tassert_mediane)


data = {
    'TLOC': tloc_values,
    'WMC': wmc_values,
    'TASSERT': tassert_values,
}

df = pd.DataFrame(data)

# Création des boîtes à moustaches
plt.boxplot([df['TLOC'], df['WMC'], df['TASSERT']], labels=['TLOC', 'WMC', 'TASSERT'])

# Ajoute titres des axes
plt.title('Boîtes à moustaches pour TLOC, WMC et TASSERT')
plt.xlabel('Métriques')
plt.ylabel('Nombres')


### WMC en fonction de TASSERT ###

# Create new figure
plt.figure()

# Calculate key values
slope, intercept, r, p, std_err = stats.linregress(tassert_values, wmc_values)

# Function to calculate y using mx + b
def calcY(x):
    return slope * x + intercept

# Linear regression (y = mx + b)
linreg = list(map(calcY, tassert_values))

# Create graph
plt.scatter(tassert_values, wmc_values)

# Label axes and title
plt.title('WMC en fonction de TASSERT')
plt.xlabel('TASSERT')
plt.ylabel('WMC')

# Plot regression line
plt.plot(tassert_values,linreg,'k',label='y={:.2f}x+{:.2f}'.format(slope,intercept))
plt.legend(loc='upper left')

# Show r^2 value on plot
plt.annotate("r^2 = {:.3f}".format(r**2), xy=(225,50))

### TLOC en fonction de TASSERT ###

# Create new figure
plt.figure()

# Calculate key values
slope, intercept, r, p, std_err = stats.linregress(tassert_values, tloc_values)

# Function to calculate y using mx + b
def calcY(x):
    return slope * x + intercept

# Linear regression (y = mx + b)
linreg = list(map(calcY, tassert_values))

# Create graph
plt.scatter(tassert_values, tloc_values)

# Label axes and title
plt.title('TLOC en fonction de TASSERT')
plt.xlabel('TASSERT')
plt.ylabel('TLOC')

# Plot regression line
plt.plot(tassert_values,linreg,color='k',label='y={:.2f}x+{:.2f}'.format(slope,intercept))
plt.legend(loc='upper left')

# Show r^2 value on plot
plt.annotate("r^2 = {:.3f}".format(r**2), xy=(225,650))

plt.show()

# source : https://www.w3schools.com/python/ref_stat_median.asp#:~:text=The%20statistics.,in%20a%20set%20of%20data.
# https://www.data-transitionnumerique.com/boite-moustache-boxplot/
# https://www.w3schools.com/python/python_ml_linear_regression.asp 