# Kerbs Data Structure Library

Shared JPA entities and DTOs library for Kerbs Solutions microservices architecture.

## 📦 Package Information

- **Group ID**: `org.kerbs_common`
- **Artifact ID**: `data_structure`  
- **Current Version**: `1.1.0`
- **Java Version**: 21
- **Spring Boot**: 3.4.1

## 🚀 Installation

### Option 1: From GitHub Packages (Recommended)

Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.kerbs_common</groupId>
    <artifactId>data_structure</artifactId>
    <version>1.1.0</version>
</dependency>
```

Add the GitHub Packages repository:

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/kerbs-solutions/data_structure</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
```

### Authentication for GitHub Packages

Create `~/.m2/settings.xml` with your GitHub token:

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                              http://maven.apache.org/xsd/settings-1.0.0.xsd">
  <servers>
    <server>
      <id>github</id>
      <username>YOUR_GITHUB_USERNAME</username>
      <password>YOUR_PERSONAL_ACCESS_TOKEN</password>
    </server>
  </servers>
</settings>
```

### Option 2: Local Installation (Development)

For local development, build and install to your local Maven repository:

```bash
cd data_structure
./mvnw clean install
```

## 📋 Entities Included

### Core Entities
- **Cliente** (Abstract base class with TABLE_PER_CLASS inheritance)
  - **Empresa** - Corporate clients
  - **ClienteFinal** - Individual clients
- **Heladera** - Refrigeration equipment
- **TipoHeladera** - Equipment types

### Pricing System (V005 Architecture)
- **Precio** - Auto-increment ID + legacy codigo compatibility
  - **ManoDeObra** - Labor pricing
  - **Repuesto** - Parts pricing

### Services & Operations
- **Servicio** (Abstract base)
  - **Acondicionamiento** - Conditioning services
  - **Viaje** - Travel services
- **Facturable** (Abstract base)
  - **Reparacion** - Repair operations
  - **Movimiento** - Inventory movements

### Enums
- **EstadoServicio** - Service states
- **EstadoOperacion** - Operation states  
- **EstadoContableServicio** - Accounting states

## 🏗️ Architecture Features

### V005 Pricing Architecture
- **Auto-increment Long IDs** for scalable relationships
- **Legacy codigo compatibility** for backward compatibility
- **Globally unique legacy codigos** across all pricing plans
- **NULL codigo support** for new pricing entries
- **Soft delete support** with `activo` flag

### TABLE_PER_CLASS Inheritance
- **Cliente hierarchy**: Separate `empresa` and `cliente_final` tables
- **Precio hierarchy**: Separate `mano_de_obra` and `repuesto` tables
- **Optimized for JPA queries** and business logic separation

### Dual Compatibility
- **Long ID operations** for new features
- **Legacy codigo access** for existing integrations
- **Mixed pricing scenarios** (legacy + new entries)

## 🔧 Development

### Building the Library

```bash
# Clean build
./mvnw clean compile

# Run tests
./mvnw test

# Build with sources and javadocs
./mvnw clean package

# Install locally
./mvnw clean install
```

### Publishing New Versions

1. **Update version** in `pom.xml`
2. **Commit changes** and push to main branch
3. **Create and push tag**:
   ```bash
   git tag v1.1.0
   git push origin v1.1.0
   ```
4. **GitHub Actions** will automatically build and publish to GitHub Packages

### Manual Publishing

```bash
# Deploy to GitHub Packages (requires authentication)
./mvnw clean deploy
```

## 🔗 Usage in Microservices

### kerbs-operations
- Uses all entities for operations management
- V005 pricing architecture with auto-increment IDs
- Cliente hierarchy for client management

### kerbs-informes  
- Financial calculations and reporting
- Legacy codigo compatibility maintained
- Integration with pricing and service entities

## 📖 Documentation

- **API Documentation**: Auto-generated Javadocs included in releases
- **Entity Relationships**: Defined through JPA annotations
- **Business Logic**: Documented in entity classes

## 🔒 Security & Authentication

- **GitHub Packages**: Requires GitHub Personal Access Token
- **Private Repository**: Access controlled by repository permissions
- **CI/CD Integration**: Uses `GITHUB_TOKEN` for automated publishing

## 📦 Releases

Latest releases available at: https://github.com/kerbs-solutions/data_structure/packages/

### Version History
- **v1.1.0** - V005 pricing architecture, leader validation compliance
- **v1.0.6** - V004 pricing plan implementation
- **v1.0.x** - Cliente hierarchy and basic entities

## 🤝 Contributing

1. Fork the repository
2. Create feature branch
3. Make changes and test
4. Update version number
5. Submit pull request
6. After merge, create release tag

---

## 🏛️ Legacy Instructions (Deprecated)

<details>
<summary>Old local-only installation method</summary>

### Legacy Local Installation
```bash
cd /path/to/Kerbs-solutions/data-structure
mvn clean install
```

Add dependency (old groupId):
```xml
<dependency>
    <groupId>org.kerbs.common</groupId>
    <artifactId>data_structure</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Force Maven reindexing:
```bash
mvn dependency:purge-local-repository
```
</details>