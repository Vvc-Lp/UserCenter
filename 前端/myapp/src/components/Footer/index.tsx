import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
const Footer: React.FC = () => {
  const defaultMessage = 'Vvc出品';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'baidu',
          title: '百度',
          href: 'https://www.baidu.com',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <><GithubOutlined />Vvc Github</>,
          href: 'https://github.com/vvc-Lp',
          blankTarget: true,
        },
        {
          key: 'bilibili',
          title: 'bilibili',
          href: 'https://bilibili.com',
          blankTarget: true,
        },
      ]}
    />
  );
};
export default Footer;
