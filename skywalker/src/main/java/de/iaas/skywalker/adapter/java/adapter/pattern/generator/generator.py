
SPACE = ' '
TAB = '\t'
NEWLINE = '\n'

genericPropertyName = {
    'getFunction': 'function',
    'getEvents': 'events',
    'getInvokedServices': 'invokedServices'
}

def get_method_name(line):
    for word in line.strip().split(' '):
        if 'get' in word:
            return word.replace('()', '').replace(';', '')

def contains_abstract_method(line):
    if line.strip()[-3:] == '();' and '=' not in line:
        return True
    return False

def provider_adapter_generation(adapter, provider, provider_mapping_lookup):
    open('../' + provider + adapter+'.java', "x")
    method_repository = {}
    generated_class = {}
    with open('../' + adapter + '.java') as file:

        for line_number, line in enumerate(file, 0):
            if 'interface' in line:
                line = line.replace('interface', 'class')
                if adapter in line:
                    line = line.replace(adapter, provider + adapter + SPACE + 'implements' + SPACE + adapter)
                    line += NEWLINE + TAB + 'private GenericApplicationModel GAM;'
                    line += NEWLINE + NEWLINE
                    line += TAB + 'public' + SPACE + provider + adapter + '(GenericApplicationModel GAM)' + SPACE + '{'
                    line += NEWLINE
                    line += TAB + TAB + 'this.GAM = GAM;'
                    line += NEWLINE
                    line += TAB + '}'
                    line += NEWLINE

            if contains_abstract_method(line):
                method_name = get_method_name(line)
                method_header = TAB + 'public' + SPACE + line.replace(';\n', '').strip() + SPACE + '{'
                method_body = TAB + TAB + 'return' + SPACE + method_name + provider + '(' + 'GAM' + '.' + method_name + '()' + ')' + ';'
                closing_bracket = TAB + '}'

                line += TAB + '@Override' + NEWLINE
                line = method_header
                line += NEWLINE
                line += method_body
                line += NEWLINE
                line += closing_bracket
                line += NEWLINE

                method_repository[method_name + SPACE + provider] = {
                    'header': method_header,
                    'body': TAB + TAB + 'return null;',
                    'closing': closing_bracket
                }
                line = TAB + '@Override' + NEWLINE + line

            generated_class[line_number] = line

    with open('../' + provider + adapter + '.java', "a") as newfile:
        if generated_class.get(len(generated_class)-1).strip() == '}':
            generated_class[len(generated_class)-1] = generate_adapter_methods(method_repository, provider_mapping_lookup) + NEWLINE + '}'
        for key, value in generated_class.items():
            newfile.write(value)


def generate_adapter_methods(method_repository, provider_mapping_lookup):
    adapter_method_lines = ''
    method_keys = list(method_repository)
    for i in range(len(method_repository)):
        method_name = method_keys[i].split(' ')[0]
        print(method_name)
        method_content = method_repository[method_keys[i]]
        adapter_method_header = method_content['header'].replace(method_name, method_keys[i].replace(' ', ''))
        adapter_method_header = adapter_method_header.replace('()', '(String genericProperty)')
        adapter_method_body = method_content['body'].replace('null;', '\"' + provider_mapping_lookup[genericPropertyName[method_name]] + '\";')
        adapter_method_lines += adapter_method_header + NEWLINE + adapter_method_body + NEWLINE + method_content['closing'] + NEWLINE
    return adapter_method_lines


if __name__ == '__main__':

    provider_mapping_lookup_azure = {
        'function': 'scriptFile',
        'events': 'inputBinding',
        'invokedServices': 'outputBinding'
    }

    provider_adapter_generation('ApplicationModelAdapter', 'IBM', provider_mapping_lookup_azure)
