#!/bin/bash

echo "=== ПРОВЕРКА ПРОЕКТА LABWORK3 ==="
echo "Время: $(date)"
echo ""

echo "1. СТРУКТУРА ПАПОК:"
echo "   src/main/java/ru/rmntim/web/ - $(ls -d src/main/java/ru/rmntim/web/*/ 2>/dev/null | wc -l) подпапок"
ls -d src/main/java/ru/rmntim/web/*/ 2>/dev/null | sed 's|/$||' | sed 's|.*/||' | while read dir; do
    echo "   - $dir"
done

echo ""
echo "2. JAVA КЛАССЫ:"
declare -A required_classes=(
    ["Point.java"]="models"
    ["PointBean.java"]="beans"
    ["TimeBean.java"]="beans" 
    ["PointChecker.java"]="services"
    ["DatabaseManager.java"]="dao"
    ["PointDao.java"]="dao"
)

for class in "${!required_classes[@]}"; do
    dir="${required_classes[$class]}"
    if [ -f "src/main/java/ru/rmntim/web/$dir/$class" ]; then
        echo "   ✓ $class (в папке $dir)"
    else
        echo "   ✗ $class (отсутствует в папке $dir)"
    fi
done

echo ""
echo "3. WEB ФАЙЛЫ:"
required_web=("index.xhtml" "main.xhtml" "WEB-INF/web.xml" "WEB-INF/faces-config.xml" "WEB-INF/beans.xml" "resources/css/lab3.css")
for file in "${required_web[@]}"; do
    if [ -f "src/main/webapp/$file" ]; then
        echo "   ✓ $file"
    else
        echo "   ✗ $file"
    fi
done

echo ""
echo "4. DOCKER ФАЙЛЫ:"
required_docker=("docker-compose.yml" "wildfly/Dockerfile" "wildfly/module.xml" "wildfly/create-datasource.cli" "wildfly/ojdbc11.jar")
for file in "${required_docker[@]}"; do
    if [ -f "docker/$file" ]; then
        echo "   ✓ $file"
    else
        echo "   ✗ $file"
    fi
done

echo ""
echo "5. РЕСУРСЫ:"
if [ -f "src/main/resources/sql/create_tables.sql" ]; then
    echo "   ✓ sql/create_tables.sql"
else
    echo "   ✗ sql/create_tables.sql"
fi

echo ""
echo "=== ИТОГО ==="
